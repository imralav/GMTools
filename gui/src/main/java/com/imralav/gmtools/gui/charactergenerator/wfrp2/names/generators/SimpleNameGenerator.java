package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.SimpleNamesLibrary;
import com.imralav.gmtools.gui.utils.Randomizer;

import java.util.List;
import java.util.function.Function;

public abstract class SimpleNameGenerator<L extends SimpleNamesLibrary> implements NameGenerator {
    static final String FULL_NAME_FORMAT = "%s %s";

    abstract L getLibrary();

    @Override
    public String generateSimpleName(Gender gender) {
        return generateGenderSpecificName(gender, getLibrary()::getSimpleNames);
    }

    String generateGenderSpecificName(Gender gender, Function<Gender, List<String>> namesRetrievalFunction) {
        List<String> names = namesRetrievalFunction.apply(gender);
        int randomNameIndex = Randomizer.getRandomInt(names.size());
        return names.get(randomNameIndex);
    }

    String generateSurname() {
        List<String> surnames = getLibrary().getSurnames();
        int randomNameIndex = Randomizer.getRandomInt(surnames.size());
        return surnames.get(randomNameIndex);
    }
}
