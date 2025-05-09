package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Retailer;
import com.jamk.pet.food.finder.api.portal.model.RetailerDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RetailerMapperTest {

    private RetailerMapper mapper = Mappers.getMapper(RetailerMapper.class);

    @Test
    void testToRetailerDto() {
        Retailer product = new Retailer();
        product.setId("1");
        product.setName("Dog Food");
        product.setPrice(4.5);

        RetailerDto dto = mapper.toRetailerDto(product);

        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getPrice(), dto.getPrice());
    }

    @Test
    void testToRetailer() {
        RetailerDto dto = new RetailerDto();
        dto.setId("1");
        dto.setName("Cat Food");
        dto.setPrice(3.8);

        Retailer product = mapper.toRetailer(dto);

        assertEquals(dto.getId(), product.getId());
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getPrice(), product.getPrice());
    }

    @Test
    void testToRetailerDtos() {
        Retailer p1 = new Retailer(); p1.setId("1");
        Retailer p2 = new Retailer(); p2.setId("2");

        List<RetailerDto> dtos = mapper.toRetailerDtos(List.of(p1, p2));
        assertEquals(2, dtos.size());
    }

    @Test
    void testToRetailers() {
        RetailerDto d1 = new RetailerDto(); d1.setId("1");
        RetailerDto d2 = new RetailerDto(); d2.setId("2");

        List<Retailer> products = mapper.toRetailers(List.of(d1, d2));
        assertEquals(2, products.size());
    }
}
