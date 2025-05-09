package com.jamk.pet.food.finder.api.portal.repository;

import com.jamk.pet.food.finder.api.portal.model.Description;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends MongoRepository<Description, String> {

    List<Description> findAllByProductId(String productId);
}
