package com.jamk.pet.food.finder.api.admin.service;

import com.jamk.pet.food.finder.api.admin.exception.UserNotFoundException;
import com.jamk.pet.food.finder.api.admin.mapper.UserMapper;
import com.jamk.pet.food.finder.api.admin.model.Role;
import com.jamk.pet.food.finder.api.admin.model.User;
import com.jamk.pet.food.finder.api.admin.model.UserDto;
import com.jamk.pet.food.finder.api.admin.repository.UserRepository;
import com.jamk.pet.food.finder.api.product.exception.ReviewNotFoundException;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toUserDto);
    }

    public UserDto findUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Page<UserDto> searchUsers(String id, String username, String name, String surname, String email, String jobTitle,
                                     String phoneNumber, Boolean isActive, List<String> roles, Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (valueExists(id)) addIdCriteria(criteriaList, "id", id);
        if (valueExists(username)) addRegexCriteria(criteriaList, "username", username);
        if (valueExists(name)) addRegexCriteria(criteriaList, "name", name);
        if (valueExists(surname)) addRegexCriteria(criteriaList, "surname", surname);
        if (valueExists(email)) addRegexCriteria(criteriaList, "email", email);
        if (valueExists(jobTitle)) addRegexCriteria(criteriaList, "jobTitle", jobTitle);
        if (valueExists(phoneNumber)) addRegexCriteria(criteriaList, "phoneNumber", phoneNumber);
        if (valueExists(isActive)) addBooleanCriteria(criteriaList, "isActive", isActive);
        if (valueExists(roles)) addArrayCriteria(criteriaList, "roles", roles);

        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, User.class);
        query.with(pageable);

        List<UserDto> userDtos = mongoTemplate.find(query, User.class).stream().map(userMapper::toUserDto).collect(Collectors.toList());
        return new PageImpl<>(userDtos, pageable, total);
    }

    private void addBooleanCriteria(List<Criteria> list, String field, Boolean value) {
        list.add(Criteria.where(field).is(new Boolean(value)));
    }

    private boolean valueExists(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof String) {
            return !((String) value).isBlank();
        }
        if (value instanceof Collection<?>) {
            return !((Collection<?>) value).isEmpty();
        }
        return true;
    }

    private void addRegexCriteria(List<Criteria> list, String field, String value) {
        list.add(Criteria.where(field).regex(Pattern.quote(value), "i"));
    }

    private void addArrayCriteria(List<Criteria> list, String field, List<String> values) {
        list.add(Criteria.where(field).all(values));
    }

    private void addIdCriteria(List<Criteria> list, String field, String value) {
        list.add(Criteria.where("_id").is(new ObjectId(value)));
    }

    public Long getNumberOfEnabled() {
        return userRepository.countByIsActive(false);
    }

    public Optional<UserDto> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toUserDto);
    }

    public void createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);

        user.setRoles(List.of(Role.USER));
        user.setIsActive(false);

        userRepository.save(user);
    }

    public UserDto updateUser(String id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Failed to update user, user not found with id: " + id));

        User userToUpdate = userMapper.toUser(userDto);
        userToUpdate.setId(existingUser.getId());
        User savedUser = userRepository.save(userToUpdate);

        return userMapper.toUserDto(savedUser);
    }

    public void setIsActive(String id, Boolean isActive) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("User not found with id: " + id));

        user.setIsActive(isActive);
        userRepository.save(user);
    }

    public void deleterUser(String id) {
        userRepository.deleteById(id);
    }
}
