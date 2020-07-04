package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Gender;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class HumanNamesLibrary extends AbstractSimpleNamesLibrary {
    private Map<Gender, List<String>> complexNames = new EnumMap<>(Gender.class);

    public void setMaleComplexNames(List<String> maleComplexNames) {
        complexNames.put(Gender.MALE, maleComplexNames);
    }

    public void setFemaleComplexNames(List<String> femaleComplexNames) {
        complexNames.put(Gender.FEMALE, femaleComplexNames);
    }

    public List<String> getComplexNames(Gender gender) {
        return complexNames.get(gender);
    }
}
