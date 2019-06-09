package com.imralav.gmtools.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.factories.DwarfNamesLibraryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DwarfNameGenerator extends SuffixableNameGenerator {

    private final SuffixableNamesLibrary library;

    @Autowired
    public DwarfNameGenerator(DwarfNamesLibraryFactory dwarfNameLibraryFactory) throws IOException {
        this.library = dwarfNameLibraryFactory.create();
    }

    @Override
    public Race getCorrespondingRace() {
        return Race.DWARF;
    }

    @Override
    SuffixableNamesLibrary getLibrary() {
        return library;
    }
}
