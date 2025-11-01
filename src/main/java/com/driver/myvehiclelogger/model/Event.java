package com.driver.myvehiclelogger.model;

import com.driver.myvehiclelogger.model.enums.EventCategory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false ,length = 100)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column
    private Integer kilometers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory eventCategory;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private Boolean notification;

    public Long getVehicleId() {
        return vehicle.getId();
    }
}
