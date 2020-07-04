package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Gender;

import java.util.List;

public interface SimpleNamesLibrary {
    void setFemaleSimpleNames(List<String> names);
    void setMaleSimpleNames(List<String> names);
    void setSurnames(List<String> name);
    List<String> getSimpleNames(Gender gender);
    List<String> getSurnames();
}
