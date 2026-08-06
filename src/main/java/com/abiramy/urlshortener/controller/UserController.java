package com.abiramy.urlshortener.controller;

import com.abiramy.urlshortener.dto.request.RegisterRequest;
import com.abiramy.urlshortener.dto.response.RegisterResponse;
import com.abiramy.urlshortener.entity.User;
import com.abiramy.urlshortener.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")

public class UserController {

    private final UserService userService;

    //constructor injection
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(
            @RequestBody RegisterRequest request){

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        RegisterResponse response = userService.registerUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }




}
