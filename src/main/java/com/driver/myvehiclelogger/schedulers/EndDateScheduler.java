package com.driver.myvehiclelogger.schedulers;

import com.driver.myvehiclelogger.data.EventRepository;
import com.driver.myvehiclelogger.model.Event;
import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.model.Vehicle;
import com.driver.myvehiclelogger.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EndDateScheduler {
    private final EventRepository eventRepository;
    private final EmailService emailService;
    LocalDate notificationDate = LocalDate.now().plusDays(7);

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void processEventsWithNotifications() {
        List<Event> events = eventRepository.findByNotificationTrueAndEndDate(notificationDate);
        events.forEach(event -> {
            Vehicle vehicle = event.getVehicle();
            User user = vehicle.getUser();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
            String formatedDate = event.getEndDate().format(formatter);

            System.out.println(user.getEmail() + " " +
                    event.getName() + " for vehicle " + vehicle.getMake() +
                            " " + vehicle.getModel() +
                            " with registration " + vehicle.getRegistration() +
                            " expires on " + formatedDate);

            emailService.sendEmail(
                    user.getEmail(),
                    event.getName() + " expire soon",
                    event.getName() + " for vehicle " + vehicle.getMake() +
                            " " + vehicle.getModel() +
                            " with registration " + vehicle.getRegistration() +
                            " expires on " + formatedDate);
        });
    }

}
