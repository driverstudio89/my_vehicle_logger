package com.driver.myvehiclelogger.mapper;

import com.driver.myvehiclelogger.model.Vehicle;
import com.driver.myvehiclelogger.web.dto.AddVehicleDto;
import com.driver.myvehiclelogger.web.dto.VehicleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {


//    @Mapping(target = "owner", expression = "java(vehicle.getOwnerId())")
    VehicleDto toVehicleDto(Vehicle vehicle);

    Vehicle toEntity(AddVehicleDto addVehicleDto);

}
