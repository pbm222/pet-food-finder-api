package com.jamk.pet.food.finder.api.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private JobTitle jobTitle;
    private String phoneNumber;
    private List<Role> roles;
    private Boolean isActive;
}
