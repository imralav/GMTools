package com.imralav.gmtools.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Race;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;
import com.imralav.gmtools.charactergenerator.wfrp2.names.libraries.factories.DwarfNamesLibraryFactory;
import com.imralav.gmtools.utils.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

public abstract class SuffixableNameGenerator extends SimpleNameGenerator<SuffixableNamesLibrary> {

    @Override
    public String generateComplexName(Gender gender) {
        List<String> prefixes = getLibrary().getPrefixes();
        List<String> suffixes = getSuffixesFor(gender);
        int prefixIndex = Randomizer.getRandomInt(prefixes.size());
        int suffixIndex = Randomizer.getRandomInt(suffixes.size());
        return prefixes.get(prefixIndex) + suffixes.get(suffixIndex);
    }

    private List<String> getSuffixesFor(Gender gender) {
        if(Gender.MALE.equals(gender)) {
            return getLibrary().getMaleSuffixes();
        }
        return getLibrary().getFemaleSuffixes();
    }

    @Override
    public Race getCorrespondingRace() {
        return Race.DWARF;
    }
}
