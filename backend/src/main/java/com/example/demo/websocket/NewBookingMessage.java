package com.example.demo.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor

public class NewBookingMessage {
    private final Long customerId;
    private final String destination;
    private final LocalDateTime date;
}
