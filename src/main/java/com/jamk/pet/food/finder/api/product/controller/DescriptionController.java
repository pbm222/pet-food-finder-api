package com.jamk.pet.food.finder.api.product.controller;

import com.jamk.pet.food.finder.api.product.model.Description;
import com.jamk.pet.food.finder.api.product.service.DescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/descriptions")
@RequiredArgsConstructor
public class DescriptionController {
    private final DescriptionService descriptionService;

    @GetMapping
    public List<Description> getDescriptionsByProductId(@RequestParam String productId) {
        return descriptionService.getDescriptionsByProductId(productId);
    }

    @PostMapping
    public void createDescription(@RequestBody Description description) {
        descriptionService.createDescription(description);
    }

    @PutMapping("/{id}")
    public void updateDescription(@PathVariable String id,
                                  @RequestBody Description description) {
        descriptionService.updateDescription(id, description);
    }

    @DeleteMapping("/{id}")
    public void deleteDescription(@PathVariable String id) {
        descriptionService.deleteDescription(id);
    }
}
