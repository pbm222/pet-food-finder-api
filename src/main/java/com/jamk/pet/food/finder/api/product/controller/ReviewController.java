package com.jamk.pet.food.finder.api.product.controller;

import com.jamk.pet.food.finder.api.product.model.ActiveStatusRequest;
import com.jamk.pet.food.finder.api.product.model.Review;
import com.jamk.pet.food.finder.api.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public Page<Review> getReviews(@RequestParam(required = false) String id,
                                   @RequestParam(required = false) String title,
                                   @RequestParam(required = false) String username,
                                   @RequestParam(required = false) String text,
                                   @RequestParam(required = false) String productId,
                                   @RequestParam(required = false) Boolean isActive,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return reviewService.searchReviews(id, title, username, text, productId, isActive, pageable);
    }

    @PostMapping
    public Review createReviewForProduct(@RequestBody Review review) {
        return reviewService.createReview(review);
    }

    @PatchMapping("/{id}")
    public ResponseEntity setActive(@PathVariable String id, @RequestBody ActiveStatusRequest request) {
        reviewService.setIsActive(id, request.getIsActive());
        return ResponseEntity.ok("Status updated for review with id: " + id);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleterReview(id);
    }
}
