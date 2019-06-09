package com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.factories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.HumanNameLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HumanNamesLibraryFactory extends SimpleNamesLibraryFactory<HumanNameLibrary> {
    @Autowired
    public HumanNamesLibraryFactory(ObjectMapper objectMapper) throws IOException {
        super(objectMapper);
    }

    @Override
    String jsonFileName() {
        return "human.json";
    }

    public HumanNameLibrary create() throws IOException {
        HumanNameLibrary humanNameLibrary = new HumanNameLibrary();
        populateSimpleNames(humanNameLibrary);
        populateSurnames(humanNameLibrary);
        populateComplexNames(humanNameLibrary);
        return humanNameLibrary;
    }

    private void populateComplexNames(HumanNameLibrary humanNameLibrary) throws IOException {
        JsonNode namesNode = allNamesNode.get(COMPLEX);
        List<String> femaleComplexNames = getFemaleNames(namesNode);
        List<String> maleComplexNames = getMaleNames(namesNode);
        humanNameLibrary.setFemaleComplexNames(femaleComplexNames);
        humanNameLibrary.setMaleComplexNames(maleComplexNames);
    }
}
