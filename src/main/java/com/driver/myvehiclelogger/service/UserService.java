package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.web.dto.RegisterUserRequest;
import com.driver.myvehiclelogger.web.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

public interface UserService {


    User findUserById(Long id);

    UserDto register(RegisterUserRequest request);

    Map<String, String> isAlreadyRegistered(RegisterUserRequest request);
}
