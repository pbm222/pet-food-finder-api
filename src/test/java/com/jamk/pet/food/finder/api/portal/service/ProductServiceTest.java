package com.jamk.pet.food.finder.api.portal.service;

import com.jamk.pet.food.finder.api.portal.exception.ProductNotFoundException;
import com.jamk.pet.food.finder.api.portal.mapper.ProductMapper;
import com.jamk.pet.food.finder.api.portal.model.Product;
import com.jamk.pet.food.finder.api.portal.model.ProductDto;
import com.jamk.pet.food.finder.api.portal.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock private MongoTemplate mongoTemplate;
    @Mock private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetTotalProducts() {
        when(productRepository.count()).thenReturn(20L);
        assertEquals(20L, productService.getTotalProducts());
    }

    @Test
    void testGetByIdFound() {
        Product product = new Product();
        ProductDto dto = new ProductDto();
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productMapper.toProductDto(product)).thenReturn(dto);
        assertEquals(dto, productService.getById("1"));
    }

    @Test
    void testGetByIdNotFound() {
        when(productRepository.findById("x")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getById("x"));
    }

    @Test
    void testSaveProduct() {
        Product product = new Product();
        ProductDto dto = new ProductDto();
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toProductDto(product)).thenReturn(dto);
        assertEquals(dto, productService.save(product));
    }

    @Test
    void testDeleteById() {
        productService.deleteById("id");
        verify(productRepository).deleteById("id");
    }

    @Test
    void testSetProductRating() {
        Product product = new Product();
        product.setId("1");
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);
        when(productMapper.toProductDto(any())).thenReturn(new ProductDto());

        productService.setProductRating("1", 4.5);
        verify(productRepository).save(product);
    }
}
