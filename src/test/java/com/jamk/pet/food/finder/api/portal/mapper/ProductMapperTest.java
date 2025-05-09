package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Product;
import com.jamk.pet.food.finder.api.portal.model.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void testToProductDto() {
        Product product = new Product();
        product.setId("1");
        product.setTitle("Dog Food");
        product.setRating(4.5);

        ProductDto dto = mapper.toProductDto(product);

        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getTitle(), dto.getTitle());
        assertEquals(product.getRating(), dto.getRating());
    }

    @Test
    void testToProduct() {
        ProductDto dto = new ProductDto();
        dto.setId("1");
        dto.setTitle("Cat Food");
        dto.setRating(3.8);

        Product product = mapper.toProduct(dto);

        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getTitle(), product.getTitle());
        assertEquals(dto.getRating(), product.getRating());
    }

    @Test
    void testToProductDtos() {
        Product p1 = new Product(); p1.setId("1");
        Product p2 = new Product(); p2.setId("2");

        List<ProductDto> dtos = mapper.toProductDtos(List.of(p1, p2));
        assertEquals(2, dtos.size());
    }

    @Test
    void testToProducts() {
        ProductDto d1 = new ProductDto(); d1.setId("1");
        ProductDto d2 = new ProductDto(); d2.setId("2");

        List<Product> products = mapper.toProducts(List.of(d1, d2));
        assertEquals(2, products.size());
    }
}

