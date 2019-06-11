package com.imralav.gmtools.charactergenerator.wfrp2.names.libraries;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ElfNamesLibrary extends SuffixableNamesLibrary {
    private List<String> connectors;
}
