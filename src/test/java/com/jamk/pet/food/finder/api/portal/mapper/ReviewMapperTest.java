package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Review;
import com.jamk.pet.food.finder.api.portal.model.ReviewDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ReviewMapperTest {

    private ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    void testToReviewDto() {
        Review review = new Review();
        review.setId("1");
        review.setTitle("Dog Food");
        review.setRating(4.5);
        review.setIsActive(true);
        review.setText("Some text");
        review.setProductId("123");

        ReviewDto dto = mapper.toReviewDto(review);

        assertEquals(review.getId(), dto.getId());
        assertEquals(review.getTitle(), dto.getTitle());
        assertEquals(review.getRating(), dto.getRating());
        assertEquals(review.getIsActive(), dto.getIsActive());
        assertEquals(review.getText(), dto.getText());
        assertEquals(review.getProductId(), dto.getProductId());
    }

    @Test
    void testToReview() {
        ReviewDto dto = new ReviewDto();
        dto.setId("1");
        dto.setTitle("Cat Food");
        dto.setRating(3.8);
        dto.setIsActive(true);
        dto.setText("Some text");
        dto.setProductId("123");

        Review review = mapper.toReview(dto);

        assertEquals(dto.getId(), review.getId());
        assertEquals(dto.getTitle(), review.getTitle());
        assertEquals(dto.getRating(), review.getRating());
        assertEquals(review.getIsActive(), dto.getIsActive());
        assertEquals(review.getText(), dto.getText());
        assertEquals(review.getProductId(), dto.getProductId());
    }

    @Test
    void testToReviewDtos() {
        Review p1 = new Review(); p1.setId("1");
        Review p2 = new Review(); p2.setId("2");

        List<ReviewDto> dtos = mapper.toReviewDtos(List.of(p1, p2));
        assertEquals(2, dtos.size());
    }

    @Test
    void testToReviews() {
        ReviewDto d1 = new ReviewDto(); d1.setId("1");
        ReviewDto d2 = new ReviewDto(); d2.setId("2");

        List<Review> reviews = mapper.toReviews(List.of(d1, d2));
        assertEquals(2, reviews.size());
    }
}
