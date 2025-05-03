package com.jamk.pet.food.finder.api.product.repository;

import com.jamk.pet.food.finder.api.product.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ReviewsRepository extends MongoRepository<Review, String> {

    Page<Review> findAllByProductId(String productId, Pageable pageable);
    List<Review> findAllByProductIdAndIsActive(String productId, Boolean isActive);

    Long countByIsActive(Boolean isActive);

    List<Review> findByDateBetween(LocalDateTime start, LocalDateTime end);

    @Aggregation(pipeline = {
            "{ $group: { _id: '$productId', count: { $sum: 1 } } }",  // Group by productId and count
            "{ $project: { _id: 0, productId: '$_id', count: 1 } }"    // Rename _id to productId
    })
    List<Map<String, Object>> countReviewsByProductId();
}
