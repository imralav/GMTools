package com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.factories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HalflingNamesLibraryFactory extends AbstractSuffixableNamesLibraryFactory {
    @Autowired
    HalflingNamesLibraryFactory(ObjectMapper objectMapper) throws IOException {
        super(objectMapper);
    }

    @Override
    public SuffixableNamesLibrary create() throws IOException {
        SuffixableNamesLibrary library = super.create();
        populateSurnames(library);
        return library;
    }

    @Override
    String jsonFileName() {
        return "halfling.json";
    }
}
