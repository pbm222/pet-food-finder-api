package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Description;
import com.jamk.pet.food.finder.api.portal.model.DescriptionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DescriptionMapper {
    DescriptionDto toDescriptionDto(Description description);

    Description toDescription(DescriptionDto descriptionDto);

    List<DescriptionDto> toDescriptionDtos(List<Description> descriptions);

    List<Description> toDescriptions(List<DescriptionDto> descriptionDto);
}
