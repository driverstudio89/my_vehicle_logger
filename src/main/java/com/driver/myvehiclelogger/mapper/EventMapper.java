package com.driver.myvehiclelogger.mapper;

import com.driver.myvehiclelogger.model.Event;
import com.driver.myvehiclelogger.web.dto.AddEventRequest;
import com.driver.myvehiclelogger.web.dto.EventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "vehicleId",expression = "java(event.getVehicleId())")
    EventDto toDto(Event event);

    Event toEntity(AddEventRequest addEventRequest);
}
