package com.imralav.gmtools.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;

public interface NameGenerator {
    String generateSimpleName(Gender gender);
    String generateComplexName(Gender gender);
    Race getCorrespondingRace();
}
