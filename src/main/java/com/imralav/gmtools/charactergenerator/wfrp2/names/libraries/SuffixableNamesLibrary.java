package com.imralav.gmtools.charactergenerator.wfrp2.names.libraries;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SuffixableNamesLibrary extends AbstractSimpleNamesLibrary {
    private List<String> prefixes;
    private List<String> femaleSuffixes;
    private List<String> maleSuffixes;
}
