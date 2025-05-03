package com.jamk.pet.food.finder.api.product.service;

import com.jamk.pet.food.finder.api.product.exception.DescriptionNotFoundException;
import com.jamk.pet.food.finder.api.product.model.Description;
import com.jamk.pet.food.finder.api.product.repository.DescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;

    public List<Description> getDescriptions() {
        return descriptionRepository.findAll();
    }

    public List<Description> getDescriptionsByProductId(String productId) {
        return descriptionRepository.findAllByProductId(productId);
    }

    public void createDescription(Description description) {
        descriptionRepository.save(description);
    }

    public void updateDescription(String id, Description description) {
        Description descriptionExisting = descriptionRepository.findById(id)
                .orElseThrow(() -> new DescriptionNotFoundException("Description not found by id: " + id));
        description.setId(descriptionExisting.getId());

        descriptionRepository.save(description);
    }

    public void deleteDescription(String id) {
        descriptionRepository.deleteById(id);
    }

}
