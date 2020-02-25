package com.imralav.gmtools.charactergenerator.wfrp2.names.libraries;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ElfNamesLibrary extends SuffixableNamesLibrary {
    private List<String> connectors;
}
