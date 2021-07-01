package com.example.demo.booking.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDTO {
    private Long id;
    private Long customerId;
    private String username;
    private String destination;
    private LocalDateTime date;
    private int nr_seats;
    private Float total_price;
}
