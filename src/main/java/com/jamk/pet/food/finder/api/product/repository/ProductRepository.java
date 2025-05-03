package com.jamk.pet.food.finder.api.product.repository;

import com.jamk.pet.food.finder.api.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Page<Product> findByPetTypeAndPetAgeAndPriceLessThanEqual(String petType, Integer petAge, Integer price, Pageable pageable);

    @Query("{ 'petType': ?0, 'petAge': { $eq: ?1 }, 'price': { $lte: ?2 }, 'rating': { $lte: ?3 }, 'characteristics': { $all: ?4 } }")
    Page<Product> findByPetTypeAndPetAgeAndPriceLessThanEqualAndCharacteristics(
            String petType, Integer petAge, Integer price, Integer rating, List<String> characteristics, Pageable pageable);

    List<Product> findAllByOrderByRatingDesc();

    @Query("{'id': {$regex: ?0, $options: 'i'}, 'title': {$regex: ?1, $options: 'i'}, 'brand': {$regex: ?2, $options: 'i'}, 'origin': {$regex: ?3, $options: 'i'}, 'descriptionShort': {$regex: ?4, $options: 'i'}}")
    List<Product> searchProducts(String id, String title, String brand, String origin, String descriptionShort);

    @Aggregation(pipeline = {
            "{ $unwind: '$characteristics' }",
            "{ $group: { _id: '$characteristics', count: { $sum: 1 } } }",
            "{ $project: { _id: 0, characteristic: '$_id', count: 1 } }"
    })
    List<Map<String, Object>> countProductsByCharacteristic();

}
