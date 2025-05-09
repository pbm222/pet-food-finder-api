package com.jamk.pet.food.finder.api.portal.controller;

import com.jamk.pet.food.finder.api.portal.model.RetailerDto;
import com.jamk.pet.food.finder.api.portal.service.RetailerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/retailers")
@RequiredArgsConstructor
public class RetailerController {
    private final RetailerService retailerService;

    @GetMapping
    public List<RetailerDto> getRetailersByProductId(@RequestParam String productId) {
        return retailerService.getRetailersByProductId(productId);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR')")
    @PostMapping
    public void createRetailer(@RequestBody RetailerDto retailer) {
        retailerService.createRetailer(retailer);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR', 'USER')")
    @PutMapping("/{id}")
    public void updateRetailer(@PathVariable String id, @RequestBody RetailerDto retailer) {
        retailerService.updateRetailer(id, retailer);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteRetailer(@PathVariable String id) {
        retailerService.deleteRetailer(id);
    }
}
