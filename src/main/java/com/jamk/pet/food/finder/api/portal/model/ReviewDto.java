package com.jamk.pet.food.finder.api.portal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String id;
    private String title;
    private String text;
    private Double rating;
    private LocalDateTime date;
    private String productId;
    private Boolean isActive;
}
