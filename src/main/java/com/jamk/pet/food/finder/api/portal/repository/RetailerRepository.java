package com.jamk.pet.food.finder.api.portal.repository;

import com.jamk.pet.food.finder.api.portal.model.Retailer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RetailerRepository extends MongoRepository<Retailer, String> {

    List<Retailer> findAllByProductId(String productId);
}
