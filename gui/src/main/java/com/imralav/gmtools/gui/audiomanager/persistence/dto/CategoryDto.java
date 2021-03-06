package com.imralav.gmtools.gui.audiomanager.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto implements Serializable {
    private String name;
    private boolean randomPlay;
    private boolean autoPlay;
    private List<AudioDto> sounds;
    private List<AudioDto> music;
}
