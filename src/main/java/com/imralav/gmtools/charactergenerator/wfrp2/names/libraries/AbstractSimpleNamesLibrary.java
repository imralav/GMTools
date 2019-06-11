package com.imralav.gmtools.charactergenerator.wfrp2.names.libraries;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractSimpleNamesLibrary implements SimpleNamesLibrary {
    private Map<Gender, List<String>> simpleNames = new EnumMap<>(Gender.class);
    @Getter
    @Setter
    private List<String> surnames = new ArrayList<>();

    @Override
    public void setMaleSimpleNames(List<String> maleSimpleNames) {
        simpleNames.put(Gender.MALE, maleSimpleNames);
    }

    @Override
    public void setFemaleSimpleNames(List<String> femaleSimpleNames) {
        simpleNames.put(Gender.FEMALE, femaleSimpleNames);
    }

    @Override
    public List<String> getSimpleNames(Gender gender) {
        return simpleNames.get(gender);
    }

}
