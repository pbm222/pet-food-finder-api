package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Review;
import com.jamk.pet.food.finder.api.portal.model.ReviewDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toReviewDto(Review review);

    Review toReview(ReviewDto reviewDto);

    List<ReviewDto> toReviewDtos(List<Review> reviews);

    List<Review> toReviews(List<ReviewDto> reviewDto);
}
