package com.imralav.gmtools.charactergenerator.wfrp2.names.libraries;

import com.imralav.gmtools.charactergenerator.wfrp2.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SuffixableNamesLibrary extends AbstractSimpleNamesLibrary {
    protected List<String> prefixes;
    protected List<String> femaleSuffixes;
    protected List<String> maleSuffixes;

    public void copyTo(SuffixableNamesLibrary otherLibrary) {
        otherLibrary.setFemaleSimpleNames(getSimpleNames(Gender.FEMALE));
        otherLibrary.setMaleSimpleNames(getSimpleNames(Gender.MALE));
        otherLibrary.setFemaleSuffixes(getFemaleSuffixes());
        otherLibrary.setMaleSuffixes(getMaleSuffixes());
        otherLibrary.setPrefixes(getPrefixes());
        otherLibrary.setSurnames(getSurnames());
    }
}
