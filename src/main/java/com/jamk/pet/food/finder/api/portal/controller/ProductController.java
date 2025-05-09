package com.jamk.pet.food.finder.api.portal.controller;

import com.jamk.pet.food.finder.api.portal.model.Product;
import com.jamk.pet.food.finder.api.portal.model.ProductDto;
import com.jamk.pet.food.finder.api.portal.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<ProductDto> searchProducts(@RequestParam(required = false) String id,
                                           @RequestParam(required = false) String title,
                                           @RequestParam(required = false) String brand,
                                           @RequestParam(required = false) String origin,
                                           @RequestParam(required = false) String descriptionShort,
                                           @RequestParam(required = false) String petType,
                                           @RequestParam(required = false) Integer petAge,
                                           @RequestParam(required = false) Double price,
                                           @RequestParam(required = false) Double rating,
                                           @RequestParam(required = false) List<String> characteristics,
                                           @RequestParam(required = false) String searchParam,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productService.searchProductsByParams(id, title, brand, origin, descriptionShort,
                petType, petAge, price, rating, characteristics, searchParam, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable String id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR')")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable String id) {
        productService.deleteById(id);
        return ResponseEntity.ok("Product is deleted");
    }
}
