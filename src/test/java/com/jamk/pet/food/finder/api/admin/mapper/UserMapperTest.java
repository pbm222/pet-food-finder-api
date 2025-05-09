package com.jamk.pet.food.finder.api.admin.mapper;

import com.jamk.pet.food.finder.api.admin.model.JobTitle;
import com.jamk.pet.food.finder.api.admin.model.User;
import com.jamk.pet.food.finder.api.admin.model.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Test
    void testToUserDto() {
        User user = new User();
        user.setId("1");
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john@john.com");
        user.setIsActive(true);
        user.setJobTitle(JobTitle.CEO);
        user.setPassword("password");

        UserDto dto = mapper.toUserDto(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getSurname(), dto.getSurname());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getIsActive(), dto.getIsActive());
        assertEquals(user.getJobTitle(), dto.getJobTitle());
        assertEquals(user.getIsActive(), dto.getIsActive());
    }

    @Test
    void testToUser() {
        UserDto dto = new UserDto();
        dto.setId("1");
        dto.setSurname("Doe");
        dto.setEmail("john@john.com");
        dto.setIsActive(true);
        dto.setJobTitle(JobTitle.CEO);
        dto.setPassword("password");

        User user = mapper.toUser(dto);

        assertEquals(dto.getId(), user.getId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getSurname(), dto.getSurname());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getIsActive(), dto.getIsActive());
        assertEquals(user.getJobTitle(), dto.getJobTitle());
        assertEquals(user.getIsActive(), dto.getIsActive());
    }

    @Test
    void testToUserDtos() {
        User p1 = new User(); p1.setId("1");
        User p2 = new User(); p2.setId("2");

        List<UserDto> dtos = mapper.toUserDtos(List.of(p1, p2));
        assertEquals(2, dtos.size());
    }

    @Test
    void testToUsers() {
        UserDto d1 = new UserDto(); d1.setId("1");
        UserDto d2 = new UserDto(); d2.setId("2");

        List<User> users = mapper.toUsers(List.of(d1, d2));
        assertEquals(2, users.size());
    }
}

