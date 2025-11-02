package com.driver.myvehiclelogger.service;

public interface EmailService {
    void sendEmail(String recipient, String subject, String body);
}
