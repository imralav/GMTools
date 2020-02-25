package com.imralav.gmtools.charactergenerator.wfrp2.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.imralav.gmtools.charactergenerator.wfrp2.model.Career;

import java.io.IOException;

public class CareerDeserializer extends StdDeserializer<Career> {
    protected CareerDeserializer(Class<?> vc) {
        super(vc);
    }

    protected CareerDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected CareerDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public Career deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}
