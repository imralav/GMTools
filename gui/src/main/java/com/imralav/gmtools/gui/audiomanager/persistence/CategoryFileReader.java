package com.imralav.gmtools.gui.audiomanager.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.gui.audiomanager.model.AudioManager;
import com.imralav.gmtools.gui.audiomanager.model.Category;
import com.imralav.gmtools.gui.audiomanager.persistence.converters.CategoryConverter;
import com.imralav.gmtools.gui.audiomanager.persistence.dto.CategoryDto;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CategoryFileReader {
    private static final TypeReference<Collection<CategoryDto>> CATEGORY_DTO_TYPE_REFERENCE = new TypeReference<Collection<CategoryDto>>() {
    };
    private final AudioManager audioManager;
    private ObjectMapper mapper = new ObjectMapper();

    public void read(File file) throws IOException {
        Collection<CategoryDto> dtos = mapper.readValue(file, CATEGORY_DTO_TYPE_REFERENCE);
        List<Category> categories = CategoryConverter.convert(dtos);
        audioManager.getCategories().setAll(categories);
    }
}
