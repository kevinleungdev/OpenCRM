package com.opencrm.app.api.input;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencrm.app.api.input.common.enums.OperatorEnum;

public class OperatorEnumTests {
    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void serialize() throws JsonProcessingException {
        OperatorEnum[] operators = OperatorEnum.values();
        assertThat(operators).isNotNull().isNotEmpty();

        String value = objectMapper.writeValueAsString(operators);
        assertThat(value)
                .contains("eq", "neq", "gt", "gte", "lt", "lte", "like", "notLike", "in", "notIn");
    }

    @Test
    void deserialize() throws JsonMappingException, JsonProcessingException {
        List<OperatorEnum> operators = objectMapper.readValue(
                "[\"eq\", \"neq\", \"gt\", \"gte\", \"lt\", \"lte\", \"like\", \"notLike\", \"in\", \"notIn\"]",
                new TypeReference<List<OperatorEnum>>() {
                });

        assertThat(operators).hasSize(10);
        assertThat(operators).element(0).isEqualTo(OperatorEnum.EQUAL);
    }
}
