package com.imralav.gmtools.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.SimpleNamesLibrary;
import com.imralav.gmtools.utils.Randomizer;

import java.util.List;
import java.util.function.Function;

public abstract class SimpleNameGenerator<L extends SimpleNamesLibrary> implements NameGenerator {
    static final String FULL_NAME_FORMAT = "%s %s";

    abstract L getLibrary();

    @Override
    public String generateSimpleName(Gender gender) {
        return generateName(gender, getLibrary()::getSimpleNames);
    }

    String generateName(Gender gender, Function<Gender, List<String>> namesRetrievalFunction) {
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
