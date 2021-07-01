package com.example.demo;

import com.example.demo.booking.BookingRepository;
import com.example.demo.booking.BookingService;
import com.example.demo.booking.dto.BookingDTO;
import com.example.demo.booking.model.Booking;
import com.example.demo.email.EmailService;
import com.example.demo.flights.FlightRepository;
import com.example.demo.flights.FlightService;
import com.example.demo.flights.dto.FlightDTO;
import com.example.demo.flights.model.Flight;
import com.example.demo.sms.model.*;
import com.example.demo.security.AuthService;
import com.example.demo.security.dto.SignupRequest;
import com.example.demo.sms.SMSService;
import com.example.demo.user.RoleRepository;
import com.example.demo.user.UserRepository;
import com.example.demo.user.dto.UserListDTO;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.UserService;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserService userService;
    private final FlightService flightService;

    private final FlightRepository flightRepository;

    private final BookingService bookingService;

    private final BookingRepository bookingRepository;

    private final AuthService authService;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookingRepository.deleteAll();
            flightRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();


            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("admin@email.com")
                    .username("admin")
                    .password("Adminpass!1")
                    .name("Roxi Admin")
                    .phone_number("+40747808149")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("employee@email.com")
                    .username("employee")
                    .password("Employeepass!1")
                    .name("Roxi Employee")
                    .phone_number("+40747808149")
                    .roles(Set.of("EMPLOYEE"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("customer1@email.com")
                    .username("customer1")
                    .password("Customerpass!1")
                    .name("George Cocis")
                    .phone_number("+40747808149")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("roxana_ciocian@yahoo.com")
                    .username("customer2")
                    .password("Customerpass!1")
                    .name("Roxana Ciocian")
                    .phone_number("+40747808149")
                    .roles(Set.of("CUSTOMER"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("customer3@yahoo.com")
                    .username("customer3")
                    .password("Customerpass!1")
                    .name("Gabriela Truta")
                    .phone_number("+40747808149")
                    .roles(Set.of("CUSTOMER"))
                    .build());

            User customer1 = userRepository.findByUsername("customer1")
                    .orElseThrow(() -> new EntityNotFoundException("Customer Not Found with username: "));

            User customer2 = userRepository.findByUsername("customer2")
                    .orElseThrow(() -> new EntityNotFoundException("Customer Not Found with username: "));

            User customer3 = userRepository.findByUsername("customer3")
                    .orElseThrow(() -> new EntityNotFoundException("Customer Not Found with username: "));

            FlightDTO flight1 = FlightDTO.builder()
                    .destination("Grecia")
                    .dateTime(LocalDateTime.of(2021, 06, 12, 13, 30))
                    .available_seats(45)
                    .ticket_price(21.7f)
                    .build();
            flightService.createFlight(flight1);

            Flight fl1 = flightRepository.findByDateTime(flight1.getDateTime()).orElseThrow(() -> new EntityNotFoundException("Flight Date Not Found : "));


            FlightDTO flight2 = FlightDTO.builder()
                    .destination("Turcia")
                    .dateTime(LocalDateTime.of(2021, 06, 15, 15, 30))
                    .available_seats(50)
                    .ticket_price(30.2f)
                    .build();
            flightService.createFlight(flight2);

            Flight fl2 = flightRepository.findByDateTime(flight2.getDateTime()).orElseThrow(() -> new EntityNotFoundException("Flight Date Not Found: "));

            FlightDTO flight3 = FlightDTO.builder()
                    .destination("Olanda")
                    .dateTime(LocalDateTime.of(2021, 06, 16, 20, 25))
                    .available_seats(42)
                    .ticket_price(39.8f)
                    .build();
            flightService.createFlight(flight3);

            Flight fl3 = flightRepository.findByDateTime(flight3.getDateTime()).orElseThrow(() -> new EntityNotFoundException("Flight Date Not Found: "));;

            BookingDTO bookingDTO1 = BookingDTO.builder()
                    .customerId(customer1.getId())
                    .destination("Grecia")
                    .nr_seats(6)
                    .build();

            bookingService.createBoookedFlight(bookingDTO1);

            BookingDTO bookingDTO2 = BookingDTO.builder()
                    .customerId(customer2.getId())
                    .destination("Turcia")
                    .nr_seats(8)
                    .build();

            bookingService.createBoookedFlight(bookingDTO2);


            BookingDTO bookingDTO3 = BookingDTO.builder()
                    .customerId(customer3.getId())
                    .destination("Olanda")
                    .nr_seats(2)
                    .build();
            BookingDTO bookingDTO4 = BookingDTO.builder()
                    .customerId(customer3.getId())
                    .destination("Olanda")
                    .nr_seats(5)
                    .build();
            bookingService.createBoookedFlight(bookingDTO3);
            //bookingService.updateNrSeats(134l, bookingDTO4);

//            System.out.println(bookingService.allBookings());
//            System.out.println(userService.allCustomers());
//            System.out.println(bookingService.verifyBooking(bookingDTO2));
//            System.out.println(bookingService.allBookingsForCustomer(customer1.getId()));
//            System.out.println(flightService.noAvailableSeats());

        }
    }
}
