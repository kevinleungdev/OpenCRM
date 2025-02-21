package com.opencrm.app.api.input;

import static com.opencrm.app.api.input.common.enums.OperatorEnum.EQUAL;
import static com.opencrm.app.api.input.common.enums.OperatorEnum.GREATER_THAN;
import static com.opencrm.app.api.input.common.enums.OperatorEnum.LIKE;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencrm.app.json.DateFilterItem;
import com.opencrm.app.json.StringFilterItem;

public class BaseFilterTests {

    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = JsonMapper
                .builder()
                .build();

        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void whenSerializeStringFilterItem_thenCorrect() throws JsonProcessingException {
        StringFilterItem filterItem = new StringFilterItem(EQUAL, "Test");
        String json = objectMapper.writeValueAsString(filterItem);

        assertThat(json).isEqualTo("{\"eq\":\"Test\"}");
    }

    @Test
    void whenDeserializeStringFilterItem_thenCorrect() throws JsonProcessingException {
        String stringFilterItemJson = "{\"like\":\"Test\"}";

        StringFilterItem desFilterItem = objectMapper.readValue(
                stringFilterItemJson, new TypeReference<StringFilterItem>() {
                });

        assertThat(desFilterItem).isNotNull();
        assertThat(desFilterItem.getOperator()).isEqualTo(LIKE);
        assertThat(desFilterItem.getValue()).isEqualTo("Test");
    }

    @Test
    void whenSerializeDateFilterItem_thenCorrect() throws JsonProcessingException {
        DateFilterItem filterItem = new DateFilterItem(GREATER_THAN, LocalDate.of(2024, 11, 25));
        String json = objectMapper.writeValueAsString(filterItem);

        assertThat(json).isEqualTo("{\"gt\":\"2024-11-25\"}");
    }

    @Test
    void whenDeserializeDateFilterItem_thenCorrect() throws JsonProcessingException {
        String dateFilterItemJson = "{\"gt\":\"2024-11-25\"}";
        DateFilterItem desFilterItem = objectMapper.readValue(
                dateFilterItemJson, new TypeReference<DateFilterItem>() {
                });

        assertThat(desFilterItem).isNotNull();
        assertThat(desFilterItem.getOperator()).isEqualTo(GREATER_THAN);
        assertThat(desFilterItem.getValue()).isEqualTo(LocalDate.of(2024, 11, 25));
    }
}
