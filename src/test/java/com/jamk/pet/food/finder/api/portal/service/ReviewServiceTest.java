package com.jamk.pet.food.finder.api.portal.service;

import com.jamk.pet.food.finder.api.portal.mapper.ReviewMapper;
import com.jamk.pet.food.finder.api.portal.model.Review;
import com.jamk.pet.food.finder.api.portal.model.ReviewDto;
import com.jamk.pet.food.finder.api.portal.repository.ReviewsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewsRepository reviewsRepository;
    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private ProductService productService;
    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void testGetTotalReviews() {
        when(reviewsRepository.count()).thenReturn(10L);
        assertEquals(10L, reviewService.getTotalReviews());
    }

    @Test
    void testGetNumberOfInactiveReviews() {
        when(reviewsRepository.countByIsActive(false)).thenReturn(3L);
        assertEquals(3L, reviewService.getNumberOfInactiveReviews());
    }

    @Test
    void testGetNumberOfReviewsThisMonth() {
        when(reviewsRepository.findByDateBetween(any(), any())).thenReturn(List.of(new Review(), new Review()));
        assertEquals(2L, reviewService.getNumberOfReviewsThisMonth());
    }

    @Test
    void testGetMostReviewedProductId() {
        Map<String, Object> map1 = Map.of("productId", "123", "count", 5);
        Map<String, Object> map2 = Map.of("productId", "456", "count", 10);
        when(reviewsRepository.countReviewsByProductId()).thenReturn(List.of(map1, map2));
        assertEquals(Optional.of("456"), reviewService.getMostReviewedProductId());
    }

    @Test
    void testCreateReview() {
        ReviewDto dto = new ReviewDto();
        Review review = new Review();
        Review saved = new Review();
        ReviewDto savedDto = new ReviewDto();

        when(reviewMapper.toReview(dto)).thenReturn(review);
        when(reviewsRepository.save(any())).thenReturn(saved);
        when(reviewMapper.toReviewDto(saved)).thenReturn(savedDto);

        assertEquals(savedDto, reviewService.createReview(dto));
    }

    @Test
    void testSetIsActive() {
        Review review = new Review();
        review.setId("id");
        review.setProductId("product123");
        Review updated = new Review();
        ReviewDto dto = new ReviewDto();

        when(reviewsRepository.findById("id")).thenReturn(Optional.of(review));
        when(reviewsRepository.save(review)).thenReturn(updated);
        when(reviewsRepository.findAllByProductIdAndIsActive(any(), any())).thenReturn(List.of());
        when(reviewMapper.toReviewDto(updated)).thenReturn(dto);

        assertEquals(dto, reviewService.setIsActive("id", true));
    }
}

