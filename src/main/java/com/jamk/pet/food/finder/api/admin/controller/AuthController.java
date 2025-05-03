package com.jamk.pet.food.finder.api.admin.controller;

import com.jamk.pet.food.finder.api.admin.exception.UserExistsException;
import com.jamk.pet.food.finder.api.admin.exception.UserNotActiveException;
import com.jamk.pet.food.finder.api.admin.model.User;
import com.jamk.pet.food.finder.api.admin.model.UserCredentials;
import com.jamk.pet.food.finder.api.admin.model.UserDto;
import com.jamk.pet.food.finder.api.admin.service.CustomUserService;
import com.jamk.pet.food.finder.api.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserService customUserService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserCredentials request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        User user = (User) authentication.getPrincipal();
        if (!user.getIsActive()) {
            throw new UserNotActiveException("User is not activated yet");
        }
        String token =  jwtUtil.generateToken(user);

        return token != null
                ? ResponseEntity.ok(token)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        if (customUserService.findByUsername(userDto.getUsername()).isPresent()){
            throw new UserExistsException("Username already exists");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        customUserService.createUser(userDto);

        return ResponseEntity.ok("User registered successfully");
    }
}