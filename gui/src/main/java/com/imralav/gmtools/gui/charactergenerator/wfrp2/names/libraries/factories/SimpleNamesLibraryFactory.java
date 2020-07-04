package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.factories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.SimpleNamesLibrary;

import java.io.IOException;
import java.net.URL;
import java.util.List;

abstract class SimpleNamesLibraryFactory<T extends SimpleNamesLibrary> {
    private static final String NAMES_JSONS_PATH = "/character-data/wfrp2/names/";
    private static final TypeReference<List<String>> STRING_LIST_TYPE = new TypeReference<List<String>>() {
    };
    private static final String FEMALE = "female";
    private static final String MALE = "male";
    private static final String SIMPLE = "simple";
    static final String COMPLEX = "complex";
    private final ObjectMapper objectMapper;
    final JsonNode allNamesNode;

    SimpleNamesLibraryFactory(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        URL namesJsonUrl = getClass().getResource(NAMES_JSONS_PATH + jsonFileName());
        allNamesNode = objectMapper.readTree(namesJsonUrl);
    }

    abstract T create() throws IOException;
    abstract String jsonFileName();

    void populateSimpleNames(T namesLibrary) throws IOException {
        JsonNode simpleNamesNode = allNamesNode.get(SIMPLE);
        List<String> femaleSimpleNames = getFemaleNames(simpleNamesNode);
        List<String> maleSimpleNames = getMaleNames(simpleNamesNode);
        namesLibrary.setFemaleSimpleNames(femaleSimpleNames);
        namesLibrary.setMaleSimpleNames(maleSimpleNames);
    }

    void populateSurnames(T namesLibrary) throws IOException {
        JsonNode complexNamesNode = allNamesNode.get(COMPLEX);
        List<String> surnames = getNames(complexNamesNode, "surnames");
        namesLibrary.setSurnames(surnames);
    }

    List<String> getMaleNames(JsonNode simpleNamesNode) throws IOException {
        return getNames(simpleNamesNode, MALE);
    }

    List<String> getNames(JsonNode parentNode, String namesListName) throws IOException {
        JsonNode genderSimpleNamesNode = parentNode.get(namesListName);
        return objectMapper.readValue(objectMapper.treeAsTokens(genderSimpleNamesNode), STRING_LIST_TYPE);
    }

    List<String> getFemaleNames(JsonNode simpleNamesNode) throws IOException {
        return getNames(simpleNamesNode, FEMALE);
    }
}
