package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NameGeneratorFacade {
    private Map<Race, NameGenerator> nameGenerators;

    @Autowired
    public NameGeneratorFacade(List<NameGenerator> nameGenerators) {
        this.nameGenerators = nameGenerators.stream()
                .collect(Collectors.toMap(NameGenerator::getCorrespondingRace, Function.identity()));
    }

    public String generateSimpleName(Gender gender, Race race) {
        return nameGenerators.get(race).generateSimpleName(gender);
    }

    public String generateComplexName(Gender gender, Race race) {
        return nameGenerators.get(race).generateComplexName(gender);
    }
}
