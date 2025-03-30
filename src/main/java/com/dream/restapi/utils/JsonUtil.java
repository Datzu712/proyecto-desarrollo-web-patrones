package com.dream.restapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static String toJson(Object obj, String... excludeProperties) throws JsonProcessingException {
        ObjectNode node = objectMapper.valueToTree(obj);
        for (String prop : excludeProperties) {
            node.remove(prop);
        }
        return objectMapper.writeValueAsString(node);
    }
}
