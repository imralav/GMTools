package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.factories.HalflingNamesLibraryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HalflingNameGenerator extends SuffixableNameGenerator {

    private final SuffixableNamesLibrary library;

    @Autowired
    public HalflingNameGenerator(HalflingNamesLibraryFactory halflingNamesLibraryFactory) throws IOException {
        this.library = halflingNamesLibraryFactory.create();
    }

    @Override
    public String generateComplexName(Gender gender) {
        return String.format(FULL_NAME_FORMAT, super.generateComplexName(gender), generateSurname());
    }

    @Override
    public Race getCorrespondingRace() {
        return Race.HALFLING;
    }

    @Override
    SuffixableNamesLibrary getLibrary() {
        return library;
    }
}
