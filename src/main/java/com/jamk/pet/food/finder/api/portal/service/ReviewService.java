package com.jamk.pet.food.finder.api.portal.service;

import com.jamk.pet.food.finder.api.portal.exception.ReviewNotFoundException;
import com.jamk.pet.food.finder.api.portal.mapper.ReviewMapper;
import com.jamk.pet.food.finder.api.portal.model.Review;
import com.jamk.pet.food.finder.api.portal.model.ReviewDto;
import com.jamk.pet.food.finder.api.portal.repository.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewsRepository reviewsRepository;
    private final MongoTemplate mongoTemplate;
    private final ProductService productService;
    private final ReviewMapper mapper;

    public Long getTotalReviews() {
        return reviewsRepository.count();
    }

    public Long getNumberOfInactiveReviews() {
        return reviewsRepository.countByIsActive(false);
    }

    public Long getNumberOfReviewsThisMonth() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
        return (long) reviewsRepository.findByDateBetween(startOfMonth, endOfMonth).size();
    }

    public Optional<String> getMostReviewedProductId() {
        List<Map<String, Object>> countReviewsByProductId = reviewsRepository.countReviewsByProductId();

        Map<String, Integer> productIdReviewCount = countReviewsByProductId.stream()
                .filter(map -> map.get("productId") != null && map.get("count") != null)
                .map(map -> Map.of(map.get("productId"), map.get("count")))
                .flatMap(objectObjectMap -> objectObjectMap.entrySet().stream())
                .collect(Collectors.toMap(entry -> (String) entry.getKey(), entry -> (Integer) entry.getValue()));

        return productIdReviewCount.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = mapper.toReview(reviewDto);
        review.setDate(LocalDateTime.now());
        review.setIsActive(false);
        return mapper.toReviewDto(reviewsRepository.save(review));
    }

    private void recalculateRating(String productId) {
        OptionalDouble averageRating = reviewsRepository.findAllByProductIdAndIsActive(productId, true)
                .stream()
                .map(Review::getRating)
                .mapToDouble(rating -> rating)
                .average();

        if (averageRating.isPresent()) {
            double rating = averageRating.getAsDouble();
            BigDecimal bd = BigDecimal.valueOf(rating);
            bd = bd.setScale(2, RoundingMode.HALF_UP);

            productService.setProductRating(productId, bd.doubleValue());
        }
    }

    public ReviewDto setIsActive(String id, Boolean isActive) {
        Review review = reviewsRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));

        review.setIsActive(isActive);
        Review savedReview = reviewsRepository.save(review);

        recalculateRating(review.getProductId());

        return mapper.toReviewDto(savedReview);
    }

    public void deleterReview(String id) {
        reviewsRepository.deleteById(id);
    }


    public Page<ReviewDto> searchReviews(String id, String title, String username, String text, String productId, Boolean isActive, Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();

        addIdCriteria(criteriaList, "id", id);
        addRegexCriteria(criteriaList, "title", title);
        addRegexCriteria(criteriaList, "username", username);
        addRegexCriteria(criteriaList, "text", text);
        addRegexCriteria(criteriaList, "productId", productId);
        addBooleanCriteria(criteriaList, "isActive", isActive);

        Query query = new Query();
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        long total = mongoTemplate.count(query, Review.class);
        query.with(pageable);

        List<Review> reviews = mongoTemplate.find(query, Review.class);
        return new PageImpl<>(reviews, pageable, total)
                .map(mapper::toReviewDto);
    }

    private void addIdCriteria(List<Criteria> list, String field, String value) {
        if (value != null && !value.isBlank()) {
            list.add(Criteria.where("_id").is(new ObjectId(value)));
        }
    }

    private void addRegexCriteria(List<Criteria> list, String field, String value) {
        if (value != null && !value.isBlank()) {
            list.add(Criteria.where(field).regex(Pattern.quote(value), "i"));
        }
    }

    private void addBooleanCriteria(List<Criteria> list, String field, Boolean value) {
        if (value != null) {
            list.add(Criteria.where(field).is(new Boolean(value)));
        }
    }
}
