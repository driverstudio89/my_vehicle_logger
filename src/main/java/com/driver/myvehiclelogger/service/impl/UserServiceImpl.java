package com.driver.myvehiclelogger.service.impl;

import com.driver.myvehiclelogger.data.UserRepository;
import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }
}
