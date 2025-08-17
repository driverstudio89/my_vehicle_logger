package com.driver.myvehiclelogger.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "VARCHAR(50)")
    private String firstName;

    @Column(columnDefinition = "VARCHAR(50)")
    private String lastName;

    @OneToMany(targetEntity = Vehicle.class, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<Vehicle> vehicles;

}
