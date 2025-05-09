package com.jamk.pet.food.finder.api.portal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetailerDto {
    private String id;
    private String name;
    private Double price;
    private String url;
    private Boolean isAuthorized;
    private String productId;
}
