package com.imralav.gmtools.gui.audiomanager.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.gui.audiomanager.model.AudioManager;
import com.imralav.gmtools.gui.audiomanager.model.Category;
import com.imralav.gmtools.gui.audiomanager.persistence.converters.CategoryConverter;
import com.imralav.gmtools.gui.audiomanager.persistence.dto.CategoryDto;
import javafx.collections.ObservableList;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class CategoryFileWriter {
    private final AudioManager audioManager;
    private ObjectMapper mapper = new ObjectMapper();

    public void write(File file) throws IOException {
        ObservableList<Category> categories = audioManager.getCategories();
        List<CategoryDto> dtos = CategoryConverter.convertToDto(categories);
        mapper.writeValue(file, dtos);
    }
}
