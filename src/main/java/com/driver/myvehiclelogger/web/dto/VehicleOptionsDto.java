package com.driver.myvehiclelogger.web.dto;

import com.driver.myvehiclelogger.model.enums.Category;
import com.driver.myvehiclelogger.model.enums.Color;
import com.driver.myvehiclelogger.model.enums.Engine;
import lombok.Data;

import java.util.List;

@Data
public class VehicleOptionsDto {

    private List<Color> colors;

    private List<Category> categories;

    private List<Engine> engines;

}
