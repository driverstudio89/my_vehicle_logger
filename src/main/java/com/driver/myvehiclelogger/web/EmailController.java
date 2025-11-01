package com.driver.myvehiclelogger.web;

import com.driver.myvehiclelogger.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<?> sendEmail() {
        String status = emailService.sendEmail();
        System.out.println(status);
        return ResponseEntity.ok().body(status);
    }

}
