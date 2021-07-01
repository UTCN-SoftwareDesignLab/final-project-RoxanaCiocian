package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String message) {

        MimeMessage message1 = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message1,true);
            helper.setTo(to);
            helper.setText(message);
            helper.setSubject(subject);

            ClassPathResource file = new ClassPathResource("logo.jpg");
            helper.addAttachment("logo.jpg", file);

            mailSender.send(message1);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}
