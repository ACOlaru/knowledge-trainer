package com.knowledgeTrainer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;

public final class ObjectMapperProvider {

    private static final ObjectMapper MAPPER = create();

    private ObjectMapperProvider() {}

    private static ObjectMapper create() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false
        );

        return mapper;
    }

    public static ObjectMapper get() {
        return MAPPER;
    }
}