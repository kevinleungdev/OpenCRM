package com.opencrm.app.api.input.common.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperatorEnum {
    EQUAL("eq"),
    NOT_EQUAL("neq"),
    GREATER_THAN("gt"),
    GREATER_THAN_OR_EQUAL("gte"),
    LESS_THAN("lt"),
    LESS_THAN_OR_EQUAL("lte"),
    LIKE("like"),
    NOT_LIKE("notLike"),
    IN("in"),
    NOT_IN("notIn"),;

    private static Map<String, OperatorEnum> ENUM_MAP;

    private String value;

    OperatorEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    // Build an unmutable map for the String value to OperatorEnum pairs
    // Any map impl can be used
    static {
        Map<String, OperatorEnum> map = new ConcurrentHashMap<>();
        for (OperatorEnum instance : OperatorEnum.values()) {
            map.put(instance.getValue(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static OperatorEnum fromValue(String value) {
        return ENUM_MAP.get(value);
    }
}
