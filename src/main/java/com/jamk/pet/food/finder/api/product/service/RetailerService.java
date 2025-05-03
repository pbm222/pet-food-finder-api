package com.jamk.pet.food.finder.api.product.service;

import com.jamk.pet.food.finder.api.product.exception.RetailerNotFoundException;
import com.jamk.pet.food.finder.api.product.model.Retailer;
import com.jamk.pet.food.finder.api.product.repository.RetailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetailerService {

    private final RetailerRepository retailerRepository;

    public List<Retailer> getAllRetailers() {
        return retailerRepository.findAll();
    }

    public List<Retailer> getRetailersByProductId(String productId) {
        return retailerRepository.findAllByProductId(productId);
    }

    public void createRetailer(Retailer retailer) {
        retailerRepository.save(retailer);
    }

    public void updateRetailer(String id, Retailer retailer) {
        Retailer retailerExisting = retailerRepository.findById(id)
                .orElseThrow(() -> new RetailerNotFoundException("Retailer not found by id: " + id));
        retailer.setId(retailerExisting.getId());

        retailerRepository.save(retailer);
    }

    public void deleteRetailer(String id) {
        retailerRepository.deleteById(id);
    }

}
