package com.driver.myvehiclelogger.model;

import com.driver.myvehiclelogger.model.enums.Category;
import com.driver.myvehiclelogger.model.enums.Color;
import com.driver.myvehiclelogger.model.enums.Engine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@Getter
@Setter
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String registration;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Engine engine;

    @Column
    private Integer lastKilometers;

    @Column(columnDefinition = "VARCHAR(1000)")
    private String description;

    @Column
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "users_vehicles",
        joinColumns = @JoinColumn(name = "vehicles_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonBackReference
    private User user;

    @OneToMany(targetEntity = Event.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Event> events;

    @Column(nullable = false)
    private LocalDate created;

    @Column(nullable = false)
    private LocalDate updated;

    public Long getOwnerId() {
        return user.getId();
    }

    public Vehicle(Set<Event> events) {
        this.events = events;
    }
}
