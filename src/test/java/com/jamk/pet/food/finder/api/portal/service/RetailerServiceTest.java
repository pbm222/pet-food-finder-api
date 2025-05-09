package com.jamk.pet.food.finder.api.portal.service;

import com.jamk.pet.food.finder.api.portal.exception.RetailerNotFoundException;
import com.jamk.pet.food.finder.api.portal.mapper.RetailerMapper;
import com.jamk.pet.food.finder.api.portal.model.Retailer;
import com.jamk.pet.food.finder.api.portal.model.RetailerDto;
import com.jamk.pet.food.finder.api.portal.repository.RetailerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RetailerServiceTest {

    @Mock
    private RetailerRepository retailerRepository;
    @Mock private RetailerMapper retailerMapper;

    @InjectMocks
    private RetailerService retailerService;

    @Test
    void testGetAllRetailers() {
        List<Retailer> retailers = List.of(new Retailer(), new Retailer());
        when(retailerRepository.findAll()).thenReturn(retailers);
        assertEquals(retailers, retailerService.getAllRetailers());
    }

    @Test
    void testGetRetailersByProductId() {
        List<Retailer> retailers = List.of(new Retailer());
        List<RetailerDto> dtos = List.of(new RetailerDto());

        when(retailerRepository.findAllByProductId("pid")).thenReturn(retailers);
        when(retailerMapper.toRetailerDtos(retailers)).thenReturn(dtos);

        assertEquals(dtos, retailerService.getRetailersByProductId("pid"));
    }

    @Test
    void testCreateRetailer() {
        RetailerDto dto = new RetailerDto();
        Retailer retailer = new Retailer();
        when(retailerMapper.toRetailer(dto)).thenReturn(retailer);

        retailerService.createRetailer(dto);
        verify(retailerRepository).save(retailer);
    }

    @Test
    void testUpdateRetailer() {
        RetailerDto dto = new RetailerDto();
        Retailer existing = new Retailer();
        existing.setId("id");
        Retailer updated = new Retailer();

        when(retailerRepository.findById("id")).thenReturn(Optional.of(existing));
        when(retailerMapper.toRetailer(dto)).thenReturn(updated);

        retailerService.updateRetailer("id", dto);
        verify(retailerRepository).save(updated);
    }

    @Test
    void testDeleteRetailer() {
        retailerService.deleteRetailer("id");
        verify(retailerRepository).deleteById("id");
    }

    @Test
    void testUpdateRetailerNotFound() {
        when(retailerRepository.findById("x")).thenReturn(Optional.empty());
        assertThrows(RetailerNotFoundException.class, () -> retailerService.updateRetailer("x", new RetailerDto()));
    }
}

