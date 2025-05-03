package com.jamk.pet.food.finder.api.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@Document(collection = "retailers")
public class Retailer {

    @Id
    private String id;
    private String name;
    private Double price;
    private Boolean isAuthorized;
    private String productId;
}
