package com.jamk.pet.food.finder.api.portal.controller;

import com.jamk.pet.food.finder.api.portal.model.DescriptionDto;
import com.jamk.pet.food.finder.api.portal.service.DescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/descriptions")
@RequiredArgsConstructor
public class DescriptionController {
    private final DescriptionService descriptionService;

    @GetMapping
    public List<DescriptionDto> getDescriptionsByProductId(@RequestParam String productId) {
        return descriptionService.getDescriptionsByProductId(productId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR')")
    @PostMapping
    public void createDescription(@RequestBody DescriptionDto description) {
        descriptionService.createDescription(description);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR', 'USER')")
    @PutMapping("/{id}")
    public void updateDescription(@PathVariable String id,
                                  @RequestBody DescriptionDto description) {
        descriptionService.updateDescription(id, description);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteDescription(@PathVariable String id) {
        descriptionService.deleteDescription(id);
    }
}
