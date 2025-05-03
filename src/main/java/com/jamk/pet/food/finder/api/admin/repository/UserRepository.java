package com.jamk.pet.food.finder.api.admin.repository;

import com.jamk.pet.food.finder.api.admin.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Long countByIsActive(Boolean isActive);
}