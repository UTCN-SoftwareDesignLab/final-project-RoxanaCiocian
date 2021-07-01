package com.example.demo.report;

import com.example.demo.booking.BookingService;
import com.example.demo.booking.dto.BookingDTO;
import com.example.demo.booking.mapper.BookingMapper;
import com.example.demo.booking.model.Booking;
import com.example.demo.flights.FlightService;
import com.example.demo.flights.dto.FlightDTO;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.example.demo.report.ReportType.PDF;

@Service
@AllArgsConstructor
public class PdfReportService implements ReportService {
    private BookingService bookingService;
    private BookingMapper bookingMapper;
    private FlightService flightService;

    @Override
    public File downloadReport() {
        List<FlightDTO> noAvailableSeatsList = flightService.noAvailableSeats();

        PDDocument pdfDocument = new PDDocument();
        PDPage page = new PDPage();
        pdfDocument.addPage(page);

        File output = new File("Flights report.pdf");

        try{
            PDPageContentStream cs = new PDPageContentStream(pdfDocument, page);
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_BOLD_ITALIC, 11);
            cs.setLeading(15f);
            cs.newLineAtOffset(45,700);
            cs.showText("Flights with no available seats: ");
            cs.newLine();
            cs.newLine();

            for(FlightDTO flightDTO: noAvailableSeatsList){
                cs.showText("Flight ID: " + flightDTO.getId());
                cs.newLine();
                cs.showText("Destination: " + flightDTO.getDestination());
                cs.newLine();
                cs.showText("Date: " + flightDTO.getDateTime());
                cs.newLine();
                cs.showText("Nr. of seats available: " + flightDTO.getAvailable_seats());
                cs.newLine();
                cs.showText("Ticket price: " + flightDTO.getTicket_price());
                cs.newLine();
                cs.newLine();
            }

            cs.endText();
            cs.close();
            pdfDocument.save(output);

        } catch (IOException e) {
            e.printStackTrace();
            //return "Failed PDF Report";
        }

        return output;
    }

    @Override
    public File downloadTickets(Long id) {
        BookingDTO booking = bookingMapper.toDto(bookingService.findById(id));
        PDDocument pdfTicket = new PDDocument();
        PDPage page = new PDPage();
        pdfTicket.addPage(page);

        File output = new File("Ticket.pdf");

        try{
            PDPageContentStream cs = new PDPageContentStream(pdfTicket, page);
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_BOLD_ITALIC, 11);
            cs.setLeading(15f);
            cs.newLineAtOffset(45,700);
            cs.showText("Ticket: ");
            cs.newLine();
            cs.newLine();
            cs.showText("Customer id: " + booking.getCustomerId());
            cs.newLine();
            cs.showText("Customer username: " + booking.getUsername());
            cs.newLine();
            cs.showText("Destination: " + booking.getDestination());
            cs.newLine();
            cs.showText("Nr. of seats booked: " + booking.getNr_seats());
            cs.newLine();
            cs.showText("Total price: " + booking.getTotal_price());

            cs.endText();
            cs.close();
            pdfTicket.save(output);

        } catch (IOException e) {
            e.printStackTrace();
            //return "Failed PDF Report";
        }

        return output;
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
