package com.abiramy.urlshortener.service;

import com.abiramy.urlshortener.dto.request.LoginRequest;
import com.abiramy.urlshortener.dto.response.LoginResponse;
import com.abiramy.urlshortener.dto.response.RegisterResponse;
import com.abiramy.urlshortener.entity.User;
import com.abiramy.urlshortener.exception.EmailAlreadyExistsException;
import com.abiramy.urlshortener.exception.InvalidCredentialsException;
import com.abiramy.urlshortener.repository.UserRepository;
import com.abiramy.urlshortener.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService){
        this.userRepository = userRepository; //constructor injection
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;

    }

    public RegisterResponse registerUser(User user){
        if(user.getUsername() == null || user.getUsername().isBlank()){
            throw new IllegalArgumentException("Username is required");//validation
        }

        if(userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists.");

        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User savedUser = userRepository.save(user);

        RegisterResponse response = new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                "User registered successfully"

        );

        return response;

    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password."
            );

        }

        String token = jwtService.generateToken(user);

        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                token,
                "Login successful"
        );
    }


}
