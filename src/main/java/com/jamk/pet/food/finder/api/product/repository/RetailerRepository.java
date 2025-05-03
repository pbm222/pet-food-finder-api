package com.jamk.pet.food.finder.api.product.repository;

import com.jamk.pet.food.finder.api.product.model.Retailer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RetailerRepository extends MongoRepository<Retailer, String> {

    List<Retailer> findAllByProductId(String productId);
}
