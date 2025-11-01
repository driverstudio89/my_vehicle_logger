package com.driver.myvehiclelogger.service.impl;

import com.driver.myvehiclelogger.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public String sendEmail() {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("my-vehicle-logger.online");
            message.setTo("driver.studio.89@gmail.com");
            message.setSubject("My vehicle logger test");
            message.setText("This is a test email");

            mailSender.send(message);
            return "Email Sent";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
