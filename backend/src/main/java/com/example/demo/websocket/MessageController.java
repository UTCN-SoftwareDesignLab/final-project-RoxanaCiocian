package com.example.demo.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @MessageMapping("/notification")
    @SendTo("/booking/customer")
    public OutputMessage newConsult(NewBookingMessage newBookingMessage){
        return new OutputMessage("There is a new booking for " + newBookingMessage.getCustomerId() + " on "
                + newBookingMessage.getDate() + " having destination in " + newBookingMessage.getDestination()
        );
    }
}
