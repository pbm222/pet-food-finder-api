package com.jamk.pet.food.finder.api.portal.service;

import com.jamk.pet.food.finder.api.portal.exception.DescriptionNotFoundException;
import com.jamk.pet.food.finder.api.portal.mapper.DescriptionMapper;
import com.jamk.pet.food.finder.api.portal.model.Description;
import com.jamk.pet.food.finder.api.portal.model.DescriptionDto;
import com.jamk.pet.food.finder.api.portal.repository.DescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DescriptionService {
    private final DescriptionRepository descriptionRepository;
    private final DescriptionMapper mapper;

    public List<DescriptionDto> getDescriptions() {
        return mapper.toDescriptionDtos(descriptionRepository.findAll());
    }

    public List<DescriptionDto> getDescriptionsByProductId(String productId) {
        return mapper.toDescriptionDtos(descriptionRepository.findAllByProductId(productId));
    }

    public void createDescription(DescriptionDto descriptionDto) {
        Description description = mapper.toDescription(descriptionDto);
        descriptionRepository.save(description);
    }

    public void updateDescription(String id, DescriptionDto descriptionDto) {
        Description descriptionExisting = descriptionRepository.findById(id)
                .orElseThrow(() -> new DescriptionNotFoundException("Description not found by id: " + id));
        Description description = mapper.toDescription(descriptionDto);
        description.setId(descriptionExisting.getId());

        descriptionRepository.save(description);
    }

    public void deleteDescription(String id) {
        descriptionRepository.deleteById(id);
    }

}
