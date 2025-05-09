package com.jamk.pet.food.finder.api.portal.service;

import com.jamk.pet.food.finder.api.portal.exception.RetailerNotFoundException;
import com.jamk.pet.food.finder.api.portal.mapper.RetailerMapper;
import com.jamk.pet.food.finder.api.portal.model.Retailer;
import com.jamk.pet.food.finder.api.portal.model.RetailerDto;
import com.jamk.pet.food.finder.api.portal.repository.RetailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RetailerService {

    private final RetailerRepository retailerRepository;
    private final RetailerMapper mapper;

    public List<Retailer> getAllRetailers() {
        return retailerRepository.findAll();
    }

    public List<RetailerDto> getRetailersByProductId(String productId) {
        return mapper.toRetailerDtos(retailerRepository.findAllByProductId(productId));
    }

    public void createRetailer(RetailerDto retailerDto) {
        Retailer retailer = mapper.toRetailer(retailerDto);
        retailerRepository.save(retailer);
    }

    public void updateRetailer(String id, RetailerDto retailerDto) {
        Retailer retailerExisting = retailerRepository.findById(id)
                .orElseThrow(() -> new RetailerNotFoundException("Retailer not found by id: " + id));

        Retailer retailer = mapper.toRetailer(retailerDto);
        retailer.setId(retailerExisting.getId());

        retailerRepository.save(retailer);
    }

    public void deleteRetailer(String id) {
        retailerRepository.deleteById(id);
    }

}
