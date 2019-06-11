package com.imralav.gmtools.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.HumanNamesLibrary;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.factories.HumanNamesLibraryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HumanNameGenerator extends SimpleNameGenerator<HumanNamesLibrary> {

    private final HumanNamesLibrary humanNamesLibrary;

    @Autowired
    public HumanNameGenerator(HumanNamesLibraryFactory humanNameLibraryFactory) throws IOException {
        humanNamesLibrary = humanNameLibraryFactory.create();
    }

    @Override
    public String generateComplexName(Gender gender) {
        String name = generateGenderSpecificName(gender, humanNamesLibrary::getComplexNames);
        String surname = generateSurname();
        return String.format(FULL_NAME_FORMAT, name, surname);
    }

    @Override
    public Race getCorrespondingRace() {
        return Race.HUMAN;
    }

    @Override
    HumanNamesLibrary getLibrary() {
        return humanNamesLibrary;
    }
}
