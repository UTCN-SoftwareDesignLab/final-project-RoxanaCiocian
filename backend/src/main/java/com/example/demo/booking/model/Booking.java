package com.example.demo.booking.model;

import com.example.demo.flights.model.Flight;
import com.example.demo.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User customerId;

    @Column
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_destination", referencedColumnName="destination",  nullable = false)
    private Flight destination;

//    @ManyToOne
//    @JoinColumn(name = "flight_dateTime")
    @Column
    private LocalDateTime date;

    @Column(nullable = false)
    private int nr_seats;

    @Column
    private Float total_price;
}
