package com.jamk.pet.food.finder.api.admin.model;

import com.jamk.pet.food.finder.api.product.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ProductStatisticsDto {
    private Long numberOfProducts;
    private Long numberOfReviews;
    private Long numberOfRetailers;
    private Long numberOfReviewsMonth;
    private Long numberOfInactiveReviews;
    private Long numberOfInactiveUsers;
    private Product mostReviewedProduct;
    private List<Product> bestRatedProducts;
    private Map<String, Integer> numberOfproductsPerCharacteristic;
    private Map<String, Integer> productsPerRetailer;
}
