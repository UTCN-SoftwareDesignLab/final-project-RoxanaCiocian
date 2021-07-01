package com.example.demo.flights.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {
    private Long id;
    private String destination;
    private LocalDateTime dateTime;
    private int available_seats;
    private Float ticket_price;
}
