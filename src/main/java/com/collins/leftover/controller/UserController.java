package com.collins.leftover.controller;

import com.collins.leftover.dto.user.AuthResponseDto;
import com.collins.leftover.dto.user.RegisterRequestDto;
import com.collins.leftover.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/auth/register")
    public AuthResponseDto createUser(@Valid @RequestBody RegisterRequestDto user){
        return userService.registerUser(user);
    }

    @GetMapping("/api/users/{id}")
    public AuthResponseDto retrieveUser(@PathVariable("id") @Positive Long id){
        return userService.getUserById(id);
    }
}