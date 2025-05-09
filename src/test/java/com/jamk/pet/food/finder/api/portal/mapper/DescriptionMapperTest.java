package com.jamk.pet.food.finder.api.portal.mapper;

import com.jamk.pet.food.finder.api.portal.model.Description;
import com.jamk.pet.food.finder.api.portal.model.DescriptionDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DescriptionMapperTest {

    private DescriptionMapper mapper = Mappers.getMapper(DescriptionMapper.class);

    @Test
    void testToDescriptionDto() {
        Description description = new Description();
        description.setId("1");
        description.setTitle("Dog Food");
        description.setText("Some text");

        DescriptionDto dto = mapper.toDescriptionDto(description);

        assertEquals(description.getId(), dto.getId());
        assertEquals(description.getTitle(), dto.getTitle());
        assertEquals(description.getText(), dto.getText());
    }

    @Test
    void testToDescription() {
        DescriptionDto dto = new DescriptionDto();
        dto.setId("1");
        dto.setTitle("Cat Food");
        dto.setText("Some text");

        Description description = mapper.toDescription(dto);

        assertEquals(dto.getId(), description.getId());
        assertEquals(dto.getTitle(), description.getTitle());
        assertEquals(dto.getText(), description.getText());
    }

    @Test
    void testToDescriptionDtos() {
        Description p1 = new Description(); p1.setId("1");
        Description p2 = new Description(); p2.setId("2");

        List<DescriptionDto> dtos = mapper.toDescriptionDtos(List.of(p1, p2));
        assertEquals(2, dtos.size());
    }

    @Test
    void testToDescriptions() {
        DescriptionDto d1 = new DescriptionDto(); d1.setId("1");
        DescriptionDto d2 = new DescriptionDto(); d2.setId("2");

        List<Description> descriptions = mapper.toDescriptions(List.of(d1, d2));
        assertEquals(2, descriptions.size());
    }
}

