package com.driver.myvehiclelogger.service;

import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.web.dto.RegisterUserRequest;
import com.driver.myvehiclelogger.web.dto.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public interface UserService {


    User findUserById(Long id);

    boolean existsByEmail(String email);

    UserDto register(@Valid RegisterUserRequest request);
}
