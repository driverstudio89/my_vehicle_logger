package com.driver.myvehiclelogger.data;

import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.model.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findVehiclesByRegistration(String registration);

    List<Vehicle> findAllVehicleByOwner(User currentUser);
}
