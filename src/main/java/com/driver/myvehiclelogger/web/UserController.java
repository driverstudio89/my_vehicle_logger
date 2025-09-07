package com.driver.myvehiclelogger.web;

import com.driver.myvehiclelogger.mapper.UserMapper;
import com.driver.myvehiclelogger.model.User;
import com.driver.myvehiclelogger.service.UserService;
import com.driver.myvehiclelogger.web.dto.RegisterUserRequest;
import com.driver.myvehiclelogger.web.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(
            @Valid @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriBuilder) {
        Map<String, String> alreadyRegistered = userService.isAlreadyRegistered(request);
        if (alreadyRegistered != null) {
            return ResponseEntity.badRequest().body(
                    alreadyRegistered
            );
        }

        UserDto registeredUser = userService.register(request);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(registeredUser.getId()).toUri();
        return ResponseEntity.created(uri).body(registeredUser);
    }

}
