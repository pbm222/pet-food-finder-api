package com.jamk.pet.food.finder.api.admin.mapper;

import com.jamk.pet.food.finder.api.admin.model.User;
import com.jamk.pet.food.finder.api.admin.model.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    List<UserDto> toUserDtos(List<User> users);

    List<User> toUsers(List<UserDto> userDto);
}
