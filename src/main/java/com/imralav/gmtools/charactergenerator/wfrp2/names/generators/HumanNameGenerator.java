package com.imralav.gmtools.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.HumanNameLibrary;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.factories.HumanNamesLibraryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HumanNameGenerator extends SimpleNameGenerator<HumanNameLibrary> {

    private final HumanNameLibrary humanNameLibrary;

    @Autowired
    public HumanNameGenerator(HumanNamesLibraryFactory humanNameLibraryFactory) throws IOException {
        humanNameLibrary = humanNameLibraryFactory.create();
    }

    @Override
    public String generateComplexName(Gender gender) {
        String name = generateName(gender, humanNameLibrary::getComplexNames);
        String surname = generateSurname();
        return String.format(FULL_NAME_FORMAT, name, surname);
    }

    @Override
    public Race getCorrespondingRace() {
        return Race.HUMAN;
    }

    @Override
    HumanNameLibrary getLibrary() {
        return humanNameLibrary;
    }
}
