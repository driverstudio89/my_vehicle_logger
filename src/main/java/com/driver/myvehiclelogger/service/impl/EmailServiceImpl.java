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
    public void sendEmail(String recipient, String subject, String body) {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("my-vehicle-logger.online");
            message.setTo(recipient);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
    }
}
