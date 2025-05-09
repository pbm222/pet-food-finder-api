package com.jamk.pet.food.finder.api.admin.service;

import com.jamk.pet.food.finder.api.admin.exception.UserNotFoundException;
import com.jamk.pet.food.finder.api.admin.mapper.UserMapper;
import com.jamk.pet.food.finder.api.admin.model.Role;
import com.jamk.pet.food.finder.api.admin.model.User;
import com.jamk.pet.food.finder.api.admin.model.UserDto;
import com.jamk.pet.food.finder.api.admin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private MongoTemplate mongoTemplate;

    @InjectMocks
    private CustomUserService userService;

    @Test
    void testLoadUserByUsername_UserExists() {
        User user = new User();
        user.setUsername("test");

        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername("test");

        assertEquals("test", result.getUsername());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername("notfound"));
    }

    @Test
    void testFindUserById() {
        User user = new User();
        user.setId("1");
        UserDto dto = new UserDto();
        dto.setId("1");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(dto);

        UserDto result = userService.findUserById("1");

        assertEquals("1", result.getId());
    }

    @Test
    void testCreateUser() {
        UserDto dto = new UserDto();
        dto.setUsername("newuser");

        User user = new User();
        user.setUsername("newuser");

        when(userMapper.toUser(dto)).thenReturn(user);

        userService.createUser(dto);

        verify(userRepository).save(argThat(savedUser ->
                savedUser.getUsername().equals("newuser") &&
                        !savedUser.getIsActive() &&
                        savedUser.getRoles().contains(Role.USER)
        ));
    }

    @Test
    void testSetIsActive() {
        User user = new User();
        user.setId("1");

        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        userService.setIsActive("1", true);

        assertTrue(user.getIsActive());
        verify(userRepository).save(user);
    }
}

