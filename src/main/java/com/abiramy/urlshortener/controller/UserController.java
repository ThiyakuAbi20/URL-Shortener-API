package com.abiramy.urlshortener.controller;

import com.abiramy.urlshortener.entity.User;
import com.abiramy.urlshortener.service.UserService;
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
    public void registerUser(@RequestBody User user){

        userService.registerUser(user);
    }




}
