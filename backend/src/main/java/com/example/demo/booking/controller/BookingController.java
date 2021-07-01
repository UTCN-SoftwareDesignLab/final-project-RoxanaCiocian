package com.example.demo.booking.controller;

import com.example.demo.booking.BookingService;
import com.example.demo.booking.dto.BookingDTO;
import com.example.demo.flights.FlightService;
import com.example.demo.flights.dto.FlightDTO;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import com.example.demo.user.UserService;
import com.example.demo.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKING)
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final UserService userService;
    private final FlightService flightService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookingDTO> allBookings(){
        return bookingService.allBookings();
    }

    @GetMapping("/customers")
    public List<UserListDTO> allCustomers(){
        return userService.allCustomers();
    }

    @GetMapping("/flights")
    public List<FlightDTO> allFlights(){
        return flightService.allFlights();
    }

    @PostMapping
    public void createBooking(@RequestBody BookingDTO bookingDTO) {
         bookingService.createBoookedFlight(bookingDTO);
    }

    @PutMapping(ENTITY)
    public BookingDTO updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO){
        return bookingService.updateNrSeats(id, bookingDTO);
    }

    @GetMapping("/customer" + ENTITY)
    public List<BookingDTO> allBookingsForCustomer(@PathVariable Long id){
        return bookingService.allBookingsForCustomer(id);
    }

    @GetMapping("/customer" + DOWNLOAD + ENTITY)
    public ResponseEntity<?> downloadTicket(@PathVariable Long id) throws IOException{
        File outputFile = reportServiceFactory.getReportService(ReportType.PDF).downloadTickets(id);
        ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(Paths.get(outputFile.getAbsolutePath())));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = Ticket.pdf")
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }
}
