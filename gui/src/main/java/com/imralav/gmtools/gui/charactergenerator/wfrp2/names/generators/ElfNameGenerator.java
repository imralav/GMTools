package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.ElfNamesLibrary;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.factories.ElfNamesLibraryFactory;
import com.imralav.gmtools.gui.utils.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ElfNameGenerator extends SuffixableNameGenerator {
    private ElfNamesLibrary elfNamesLibrary;

    @Autowired
    private ElfNameGenerator(ElfNamesLibraryFactory elfNamesLibraryFactory) throws IOException {
        this.elfNamesLibrary = elfNamesLibraryFactory.create();
    }

    @Override
    SuffixableNamesLibrary getLibrary() {
        return elfNamesLibrary;
    }

    @Override
    protected String buildFinalName(String prefix, String suffix) {
        List<String> connectors = elfNamesLibrary.getConnectors();
        int connectorIndex = Randomizer.getRandomInt(connectors.size());
        String selectedConnector = connectors.get(connectorIndex);
        return prefix + selectedConnector + suffix + " " + generateSurname();
    }

    @Override
    public Race getCorrespondingRace() {
        return Race.ELF;
    }
}
