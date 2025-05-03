package com.jamk.pet.food.finder.api.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "reviews")
public class Review {

    @Id
    private String id;
    private String username;
    private String title;
    private String text;
    private Double rating;
    private LocalDateTime date;
    private String productId;
    private Boolean isActive;
}
