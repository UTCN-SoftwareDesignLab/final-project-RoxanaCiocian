package com.example.demo.booking;

import com.example.demo.booking.dto.BookingDTO;
import com.example.demo.booking.mapper.BookingMapper;
import com.example.demo.booking.model.Booking;
import com.example.demo.email.EmailService;
import com.example.demo.flights.FlightRepository;
import com.example.demo.flights.FlightService;
import com.example.demo.flights.dto.FlightDTO;
import com.example.demo.flights.mapper.FlightMapper;
import com.example.demo.flights.model.Flight;
import com.example.demo.sms.SMSService;
import com.example.demo.sms.model.SMS;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import com.example.demo.websocket.OutputMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserService userService;
    private final FlightService flightService;
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private final EmailService emailService;
    private final SMSService smsService;

    public Booking findById(Long id){
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booked flight not found: " + id));
    }

    public List<BookingDTO> allBookings(){
        return bookingRepository.findAll()
                .stream().map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookingDTO> allBookingsForCustomer(Long id){
        return bookingRepository.findByCustomerId(id)
                .stream().map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public void sendDiscountSMS(BookingDTO bookingDTO){
        User customer = userService.findCustomerById(bookingDTO.getCustomerId());
        Flight flight = flightService.findByDestination(bookingDTO.getDestination());
        FlightDTO flightDTO = flightMapper.toDto(flight);
        String smsMessage = "Hello " + customer.getName() + " ,\n\n" + "You just booked " + bookingDTO.getNr_seats() + " seats for a flight with destination: " + flightDTO.getDestination()
                + " and you've got 10% discount." + "\n\n Thank you!";
        SMS sms = SMS.builder()
                .to(customer.getPhone_number())
                .message(smsMessage)
                .build();
        smsService.send(sms);
    }

    public void sendNewBookingEmail(BookingDTO bookingDTO){
        User customer = userService.findCustomerById(bookingDTO.getCustomerId());
        Flight flight = flightService.findByDestination(bookingDTO.getDestination());
        FlightDTO flightDTO = flightMapper.toDto(flight);

        String message = "Hello " + customer.getName() + " ,\n\n" + "You just booked " + bookingDTO.getNr_seats() + " seats for a flight with destination: " + flightDTO.getDestination();
        emailService.sendMail(customer.getEmail(), "Flight booking", message);
    }

    public Float getDiscount(BookingDTO bookingDTO, Float initial_price){
        Float final_price = initial_price;

        if(bookingDTO.getNr_seats() >= 7){
            final_price = initial_price - 0.1f*initial_price;
           sendDiscountSMS(bookingDTO);
        }

        return final_price;
    }

    public Boolean verifyBooking(BookingDTO bookingDTO){
        User customer = userService.findCustomerById(bookingDTO.getCustomerId());
        Boolean ok = true;

        List<BookingDTO> allBookingsForACustomer = allBookingsForCustomer(customer.getId());
        for(BookingDTO bookingDTO1: allBookingsForACustomer){
            if(bookingDTO1.getDestination().equals(bookingDTO.getDestination()))
                ok = false;
        }
        return ok;
    }

    public void createBoookedFlight(BookingDTO bookingDTO)  {
        User customer = userService.findCustomerById(bookingDTO.getCustomerId());
        Flight flight = flightService.findByDestination(bookingDTO.getDestination());
        Boolean ok = verifyBooking(bookingDTO);

        if(ok) {
            Booking newBookedFlight = Booking.builder()
                    .id(bookingDTO.getId())
                    .customerId(customer)
                    .username(customer.getUsername())
                    .destination(flight)
                    .date(flight.getDateTime())
                    .nr_seats(bookingDTO.getNr_seats())
                    .total_price(flight.getTicket_price() * bookingDTO.getNr_seats())
                    .build();
            System.out.println("initial price: " + newBookedFlight.getTotal_price());

            if (bookingDTO.getNr_seats() <= flight.getAvailable_seats()) {

                newBookedFlight.setTotal_price(getDiscount(bookingDTO, newBookedFlight.getTotal_price()));

                System.out.println("Final price: " + newBookedFlight.getTotal_price());

                sendNewBookingEmail(bookingDTO);

                flight.setAvailable_seats(flight.getAvailable_seats() - bookingDTO.getNr_seats());
                flightRepository.save(flight);
                newBookingForCustomer(bookingMapper.toDto(newBookedFlight));
                bookingRepository.save(newBookedFlight);
            }
        }

    }

    public BookingDTO updateNrSeats(Long id, BookingDTO bookingDTO){
        Booking booking = findById(id);
        Flight flight = flightService.findByDestination(bookingDTO.getDestination());

        Float new_price = bookingDTO.getNr_seats() * flight.getTicket_price();

        flight.setAvailable_seats((flight.getAvailable_seats() + booking.getNr_seats() - bookingDTO.getNr_seats()));

        flightRepository.save(flight);

        booking.setTotal_price(new_price);
        booking.setNr_seats(bookingDTO.getNr_seats());

        return bookingMapper.toDto(bookingRepository.save(booking));
    }

    public void newBookingForCustomer(BookingDTO bookingDTO){
        messagingTemplate.convertAndSend("/booking/customer" , new OutputMessage("There is a new booking for customer id: " + bookingDTO.getCustomerId() + " on "
                + bookingDTO.getDate() + " having destination in " + bookingDTO.getDestination()));
    }
}
