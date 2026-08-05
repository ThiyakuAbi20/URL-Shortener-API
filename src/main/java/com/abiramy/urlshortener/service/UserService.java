package com.abiramy.urlshortener.service;

import com.abiramy.urlshortener.entity.User;
import com.abiramy.urlshortener.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository; //constructor injection

    }

    public void registerUser(User user){
        if(user.getUsername() == null || user.getUsername().isBlank()){
            System.out.println("username is required");//validation
            return;
        }

        if(userRepository.existsByEmail(user.getEmail())){
            System.out.println("Email already exists.");
            return;
        }

        userRepository.save(user);

        System.out.println("Successfully registered");
    }
}
