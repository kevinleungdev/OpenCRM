package com.opencrm.app.api.input;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.api.input.common.filter.StringFilterItem;
import com.opencrm.app.api.input.event.EventFilter;

public class FilterBaseTests {

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
                        "gt": "2022-01-01"
                    },
                    "endDate": {
                        "lt": "2022-01-01T00:00:00"
                    }
                }
                """;

        EventFilter filter = objectMapper.readValue(eventFilterConfig, new TypeReference<EventFilter>() {
        });
        assertThat(filter).isNotNull();
    }

    @Test
    void whenSerializeFilterItems_thenCorrect() throws JsonProcessingException {
        StringFilterItem filterItem = new StringFilterItem(OperatorEnum.EQUAL, "Test");
        String json = objectMapper.writeValueAsString(filterItem);

        assertThat(json).isEqualTo("{\"eq\":\"Test\"}");
    }

    @Test
    void whenDeserializeFilterItems_thenCorrect() throws JsonProcessingException {
        String stringFilterItemJson = "{\"like\":\"Test\"}";

        StringFilterItem desFilterItem = objectMapper.readValue(
                stringFilterItemJson, new TypeReference<StringFilterItem>() {
                });

        assertThat(desFilterItem).isNotNull();
        assertThat(desFilterItem.getOperator()).isEqualTo(OperatorEnum.LIKE);
        assertThat(desFilterItem.getValue()).isEqualTo("Test");
    }
}
