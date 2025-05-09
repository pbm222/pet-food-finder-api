package com.jamk.pet.food.finder.api.portal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String title;
    private String brand;
    private String origin;
    private PetType petType;
    private Integer petAge;
    private String descriptionShort;
    private Double price;
    private Double rating;
    private String imgUrl;
    private List<ProductCharacteristic> characteristics;
}
