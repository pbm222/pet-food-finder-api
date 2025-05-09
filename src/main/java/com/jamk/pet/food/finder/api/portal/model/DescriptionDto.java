package com.jamk.pet.food.finder.api.portal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionDto {
    private String id;
    private String title;
    private String text;
    private String productId;
}
