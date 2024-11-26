package com.opencrm.app.api.input;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.api.input.common.filter.DateFilterItem;
import com.opencrm.app.api.input.common.filter.StringFilterItem;
import com.opencrm.app.api.input.event.EventFilter;

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
    void whenDeserializeEventFilter_thenCorrect() throws JsonMappingException, JsonProcessingException {
        String eventFilterConfig = """
                {
                    "title": {
                        "eq": "Test"
                    },
                    "description": {
                        "like": "Test"
                    },
                    "startDate": {
                        "gt": "2024-11-23"
                    },
                    "endDate": {
                        "lt": "2024-12-23"
                    }
                }
                """;

        EventFilter filter = objectMapper.readValue(eventFilterConfig, new TypeReference<EventFilter>() {
        });

        assertThat(filter).isNotNull();
        assertThat(filter.getTitle().getOperator()).isEqualTo(OperatorEnum.EQUAL);
        assertThat(filter.getTitle().getValue()).isEqualTo("Test");
        assertThat(filter.getDescription().getOperator()).isEqualTo(OperatorEnum.LIKE);
        assertThat(filter.getDescription().getValue()).isEqualTo("Test");
        assertThat(filter.getStartDate().getOperator()).isEqualTo(OperatorEnum.GREATER_THAN);
        assertThat(filter.getStartDate().getValue()).isEqualTo(LocalDate.of(2024, 11,
                23));
        assertThat(filter.getEndDate().getOperator()).isEqualTo(OperatorEnum.LESS_THAN);
        assertThat(filter.getEndDate().getValue()).isEqualTo(LocalDate.of(2024, 12,
                23));
    }

    @Test
    void whenSerializeStringFilterItem_thenCorrect() throws JsonProcessingException {
        StringFilterItem filterItem = new StringFilterItem(OperatorEnum.EQUAL, "Test");
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
        assertThat(desFilterItem.getOperator()).isEqualTo(OperatorEnum.LIKE);
        assertThat(desFilterItem.getValue()).isEqualTo("Test");
    }

    @Test
    void whenSerializeDateFilterItem_thenCorrect() throws JsonProcessingException {
        DateFilterItem filterItem = new DateFilterItem(OperatorEnum.GREATER_THAN, LocalDate.of(2024, 11, 25));
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
        assertThat(desFilterItem.getOperator()).isEqualTo(OperatorEnum.GREATER_THAN);
        assertThat(desFilterItem.getValue()).isEqualTo(LocalDate.of(2024, 11, 25));
    }
}
