package com.jamk.pet.food.finder.api.product.controller;

import com.jamk.pet.food.finder.api.product.model.Retailer;
import com.jamk.pet.food.finder.api.product.service.RetailerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/retailers")
@RequiredArgsConstructor
public class RetailerController {
    private final RetailerService retailerService;

    @GetMapping
    public List<Retailer> getRetailersByProductId(@RequestParam String productId) {
        return retailerService.getRetailersByProductId(productId);
    }

    @PostMapping
    public void createRetailer(@RequestBody Retailer retailer) {
        retailerService.createRetailer(retailer);
    }

    @PutMapping("/{id}")
    public void updateRetailer(@PathVariable String id, @RequestBody Retailer retailer) {
        retailerService.updateRetailer(id, retailer);
    }

    @DeleteMapping("/{id}")
    public void deleteRetailer(@PathVariable String id) {
        retailerService.deleteRetailer(id);
    }
}
