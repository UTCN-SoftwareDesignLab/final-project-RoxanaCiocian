package com.example.demo.sms;

import com.example.demo.sms.model.SMS;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class SMSService {
    private final String ACCOUNT_SID = "account sid from twilio";

    private final String AUTH_TOKEN = "account token from twilio";

    private final String FROM_NUMBER = "+15612500624";

    public void send(SMS sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
                .create();

    }

}
