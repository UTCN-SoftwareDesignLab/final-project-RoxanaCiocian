package com.example.demo.flights.controller;

import com.example.demo.flights.FlightService;
import com.example.demo.flights.dto.FlightDTO;
import com.example.demo.report.ReportServiceFactory;
import com.example.demo.report.ReportType;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.example.demo.UrlMapping.*;

@RestController
@RequestMapping(FLIGHTS)
@RequiredArgsConstructor

public class FlightController {
    private final FlightService flightService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<FlightDTO> allFlights(){
        return flightService.allFlights();
    }

    @PostMapping
    public FlightDTO createFlight(@RequestBody FlightDTO flightDTO){
        return flightService.createFlight(flightDTO);
    }

    @PutMapping(ENTITY)
    public FlightDTO updateFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO){
        return flightService.updateFlight(id, flightDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteFlight(@PathVariable Long id){
        flightService.deleteFlight(id);
    }

    @DeleteMapping
    public void deleteAllFlights(){
        flightService.deleteAll();
    }

    @GetMapping(DOWNLOAD)
    public ResponseEntity<?> exportReport() throws IOException {

        File outputFile = reportServiceFactory.getReportService(ReportType.PDF).downloadReport();
        ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(Paths.get(outputFile.getAbsolutePath())));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = Ticket.pdf")
                .contentLength(byteArrayResource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(byteArrayResource);
    }
}
