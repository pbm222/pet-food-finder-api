package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Retailer;
import com.jamk.pet.food.finder.api.portal.model.RetailerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RetailerMapper {
    RetailerDto toRetailerDto(Retailer retailer);

    Retailer toRetailer(RetailerDto retailerDto);

    List<RetailerDto> toRetailerDtos(List<Retailer> retailers);

    List<Retailer> toRetailers(List<RetailerDto> retailerDto);
}
