package com.example.demo.flights.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Flight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 350)
    private String destination;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private int available_seats;

    @Column(nullable = false)
    private Float ticket_price;
}
