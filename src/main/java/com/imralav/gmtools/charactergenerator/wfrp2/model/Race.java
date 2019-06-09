package com.imralav.gmtools.charactergenerator.wfrp2.model;

public enum Race {
    HALFLING, DWARF, HUMAN, ELF;

    public String getBundleKey() {
        return "tab.wfrp2.characterGenerator.race." + this.name();
    }
}
