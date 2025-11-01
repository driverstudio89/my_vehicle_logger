package com.driver.myvehiclelogger.data;

import com.driver.myvehiclelogger.model.Event;
import com.driver.myvehiclelogger.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByVehicleOrderByKilometersDesc(Vehicle vehicle);

    List<Event> findByNotificationTrueAndEndDate(LocalDate date);

}
