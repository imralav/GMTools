package com.imralav.gmtools.gui.charactergenerator.wfrp2.names.generators;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Gender;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.names.libraries.SuffixableNamesLibrary;
import com.imralav.gmtools.gui.utils.Randomizer;

import java.util.List;

public abstract class SuffixableNameGenerator extends SimpleNameGenerator<SuffixableNamesLibrary> {

    @Override
    public String generateComplexName(Gender gender) {
        List<String> prefixes = getLibrary().getPrefixes();
        List<String> suffixes = getSuffixesFor(gender);
        int prefixIndex = Randomizer.getRandomInt(prefixes.size());
        int suffixIndex = Randomizer.getRandomInt(suffixes.size());
        return buildFinalName(prefixes.get(prefixIndex), suffixes.get(suffixIndex));
    }

    private List<String> getSuffixesFor(Gender gender) {
        if(Gender.MALE.equals(gender)) {
            return getLibrary().getMaleSuffixes();
        }
        return getLibrary().getFemaleSuffixes();
    }

    protected String buildFinalName(String prefix, String suffix) {
        return prefix + suffix;
    }
}
