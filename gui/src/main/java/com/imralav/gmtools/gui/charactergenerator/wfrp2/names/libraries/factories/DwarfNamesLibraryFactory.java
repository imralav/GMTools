package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DwarfNamesLibraryFactory extends AbstractSuffixableNamesLibraryFactory {
    @Autowired
    DwarfNamesLibraryFactory(ObjectMapper objectMapper) throws IOException {
        super(objectMapper);
    }

    @Override
    String jsonFileName() {
        return "dwarf.json";
    }
}
