package com.jamk.pet.food.finder.api.admin.controller;

import com.jamk.pet.food.finder.api.admin.exception.UserExistsException;
import com.jamk.pet.food.finder.api.admin.model.UserDto;
import com.jamk.pet.food.finder.api.admin.model.UserEnabledRequest;
import com.jamk.pet.food.finder.api.admin.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CustomUserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public Page<UserDto> getUsers(@RequestParam(required = false) String id,
                                  @RequestParam(required = false) String username,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String surname,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String jobTitle,
                                  @RequestParam(required = false) String phoneNumber,
                                  @RequestParam(required = false) Boolean isActive,
                                  @RequestParam(required = false) List<String> roles,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return userService.searchUsers(id, username, name, surname, email, jobTitle, phoneNumber, isActive, roles, pageable);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody UserDto userDto) {
        if (userService.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserExistsException("Username already exists");
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userService.createUser(userDto);

        return ResponseEntity.ok("User successfully created.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity setIsActive(@PathVariable String id, @RequestBody UserEnabledRequest request) {
        userService.setIsActive(id, request.getIsActive());
        return ResponseEntity.ok("Status updated for user with id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        userService.deleterUser(id);
        return ResponseEntity.ok("User is deleted");
    }
}
