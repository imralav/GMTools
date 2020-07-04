package com.imralav.gmtools.gui.audiomanager.persistence.converters;

import com.imralav.gmtools.gui.audiomanager.model.AudioEntry;
import com.imralav.gmtools.gui.audiomanager.model.Category;
import com.imralav.gmtools.gui.audiomanager.persistence.dto.AudioDto;
import com.imralav.gmtools.gui.audiomanager.persistence.dto.CategoryDto;
import lombok.NonNull;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryConverter {

    public static List<CategoryDto> convertToDto(@NonNull Collection<Category> categories) {
        return categories.stream().map(CategoryConverter::convert).collect(Collectors.toList());
    }

    public static CategoryDto convert(@NonNull Category category) {
        List<AudioDto> music = category.getMusicEntriesProperty().stream().map(CategoryConverter::convertAudio).collect(Collectors.toList());
        List<AudioDto> sounds = category.getSoundEntriesProperty().stream().map(CategoryConverter::convertAudio).collect(Collectors.toList());
        return CategoryDto.builder()
                .name(category.getName())
                .randomPlay(category.isRandomPlay())
                .autoPlay(category.isAutoPlay())
                .music(music)
                .sounds(sounds)
                .build();
    }

    private static AudioDto convertAudio(@NonNull AudioEntry audio) {
        return AudioDto.builder()
                .filePath(audio.getPath())
                .build();
    }

    public static List<Category> convert(@NonNull Collection<CategoryDto> dtos) {
        return dtos.stream().map(CategoryConverter::convert).collect(Collectors.toList());
    }

    public static Category convert(@NonNull CategoryDto dto) {
        Category category = new Category(dto.getName());
        category.setRandomPlay(dto.isRandomPlay());
        category.setAutoPlay(dto.isAutoPlay());
        dto.getMusic().forEach(music -> {
            category.addMusicEntry(new File(music.getFilePath()));
        });
        dto.getSounds().forEach(sound -> {
            category.addSoundEntry(new File(sound.getFilePath()));
        });
        return category;
    }
}
