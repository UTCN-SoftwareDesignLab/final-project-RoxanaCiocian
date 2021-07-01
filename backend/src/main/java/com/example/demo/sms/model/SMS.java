package com.example.demo.sms.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SMS {
    private String to;
    private String message;

}
