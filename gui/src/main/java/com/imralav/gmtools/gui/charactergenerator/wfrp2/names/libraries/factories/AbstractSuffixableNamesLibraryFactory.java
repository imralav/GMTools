package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.factories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;

import java.io.IOException;
import java.util.List;

public abstract class AbstractSuffixableNamesLibraryFactory extends SimpleNamesLibraryFactory<SuffixableNamesLibrary> {
    AbstractSuffixableNamesLibraryFactory(ObjectMapper objectMapper) throws IOException {
        super(objectMapper);
    }

    @Override
    public SuffixableNamesLibrary create() throws IOException {
        JsonNode complexNames = getComplexNames();
        List<String> prefixes = getNames(complexNames, "prefixes");
        List<String> femaleSuffixes = getNames(complexNames, "femaleSuffixes");
        List<String> maleSuffixes = getNames(complexNames, "maleSuffixes");
        SuffixableNamesLibrary library = SuffixableNamesLibrary.builder()
                .prefixes(prefixes)
                .femaleSuffixes(femaleSuffixes)
                .maleSuffixes(maleSuffixes)
                .build();
        populateSimpleNames(library);
        return library;
    }

    protected JsonNode getComplexNames() {
        return allNamesNode.get(COMPLEX);
    }
}
