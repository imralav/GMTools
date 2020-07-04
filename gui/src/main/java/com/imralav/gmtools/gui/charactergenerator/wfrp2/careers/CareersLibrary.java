package com.imralav.gmtools.gui.charactergenerator.wfrp2.careers;

import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Career;
import com.imralav.gmtools.gui.charactergenerator.wfrp2.model.Characteristics;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CareersLibrary {
    public List<Career> getCareersForKey(String key) {
        return Collections.singletonList(new Career(key, new Characteristics()));
    }
}
