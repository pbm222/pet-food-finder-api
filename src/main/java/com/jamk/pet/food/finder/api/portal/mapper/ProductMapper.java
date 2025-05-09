package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Product;
import com.jamk.pet.food.finder.api.portal.model.ProductDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

    List<ProductDto> toProductDtos(List<Product> products);

    List<Product> toProducts(List<ProductDto> productDto);
}
