package com.driver.myvehiclelogger.service.impl;

import com.driver.myvehiclelogger.data.UserRepository;
import com.driver.myvehiclelogger.mapper.UserMapper;
import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.model.enums.Role;
import com.driver.myvehiclelogger.service.UserService;
import com.driver.myvehiclelogger.web.dto.RegisterUserRequest;
import com.driver.myvehiclelogger.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id).orElse(null);
    }

    @Override
    public UserDto register(RegisterUserRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public Map<String, String> isAlreadyRegistered(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return Map.of("email", "Email is already registered");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return Map.of("phoneNumber", "Phone number is already registered");
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }
}
