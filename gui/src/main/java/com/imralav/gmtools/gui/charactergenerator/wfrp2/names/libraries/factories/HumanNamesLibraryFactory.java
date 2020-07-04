package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.factories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.HumanNamesLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HumanNamesLibraryFactory extends SimpleNamesLibraryFactory<HumanNamesLibrary> {
    @Autowired
    public HumanNamesLibraryFactory(ObjectMapper objectMapper) throws IOException {
        super(objectMapper);
    }

    @Override
    String jsonFileName() {
        return "human.json";
    }

    public HumanNamesLibrary create() throws IOException {
        HumanNamesLibrary humanNamesLibrary = new HumanNamesLibrary();
        populateSimpleNames(humanNamesLibrary);
        populateSurnames(humanNamesLibrary);
        populateComplexNames(humanNamesLibrary);
        return humanNamesLibrary;
    }

    private void populateComplexNames(HumanNamesLibrary humanNamesLibrary) throws IOException {
        JsonNode namesNode = allNamesNode.get(COMPLEX);
        List<String> femaleComplexNames = getFemaleNames(namesNode);
        List<String> maleComplexNames = getMaleNames(namesNode);
        humanNamesLibrary.setFemaleComplexNames(femaleComplexNames);
        humanNamesLibrary.setMaleComplexNames(maleComplexNames);
    }
}
