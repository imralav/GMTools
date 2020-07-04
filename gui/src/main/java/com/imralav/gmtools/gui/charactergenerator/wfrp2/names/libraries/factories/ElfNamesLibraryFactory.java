package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.factories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.ElfNamesLibrary;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ElfNamesLibraryFactory extends AbstractSuffixableNamesLibraryFactory {
    @Autowired
    ElfNamesLibraryFactory(ObjectMapper objectMapper) throws IOException {
        super(objectMapper);
    }

    @Override
    String jsonFileName() {
        return "elf.json";
    }

    @Override
    public ElfNamesLibrary create() throws IOException {
        List<String> connectors = getConnectors();
        ElfNamesLibrary library = new ElfNamesLibrary();
        SuffixableNamesLibrary suffixableNamesLibrary = super.create();
        suffixableNamesLibrary.copyTo(library);
        library.setConnectors(connectors);
        populateSurnames(library);
        return library;
    }

    private List<String> getConnectors() throws IOException {
        JsonNode complexNames = getComplexNames();
        return getNames(complexNames, "connectors");
    }

}
