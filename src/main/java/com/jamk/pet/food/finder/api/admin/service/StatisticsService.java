package com.jamk.pet.food.finder.api.admin.service;

import com.jamk.pet.food.finder.api.admin.model.ProductStatisticsDto;
import com.jamk.pet.food.finder.api.product.model.Product;
import com.jamk.pet.food.finder.api.product.repository.RetailerRepository;
import com.jamk.pet.food.finder.api.product.service.ProductService;
import com.jamk.pet.food.finder.api.product.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ProductService productService;
    private final ReviewService reviewService;
    private final RetailerRepository retailerRepository;

    private final CustomUserService userService;

    public ProductStatisticsDto getStatistics() {
        ProductStatisticsDto dto = new ProductStatisticsDto();

        dto.setNumberOfProducts(productService.getTotalProducts());
        dto.setBestRatedProducts(productService.getThreeBestRated());
        dto.setMostReviewedProduct(getMostReviewedProduct());
        dto.setNumberOfproductsPerCharacteristic(productService.getNumberOfProductsByCharacteristic());
        //dto.setProductsPerRetailer(productService);

        dto.setNumberOfReviews(reviewService.getTotalReviews());
        dto.setNumberOfReviewsMonth(reviewService.getNumberOfReviewsThisMonth());
        dto.setNumberOfInactiveReviews(reviewService.getNumberOfInactiveReviews());

        dto.setNumberOfInactiveUsers(userService.getNumberOfEnabled());
        dto.setNumberOfRetailers(retailerRepository.count());

        return dto;
    }

    private Product getMostReviewedProduct() {
        return reviewService.getMostReviewedProductId()
                .map(productService::getById)
                .orElse(null);
    }
}
