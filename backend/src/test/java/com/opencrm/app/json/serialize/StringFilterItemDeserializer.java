package com.opencrm.app.json.serialize;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.json.StringFilterItem;

public class StringFilterItemDeserializer extends JsonDeserializer<StringFilterItem> {

    @Override
    public StringFilterItem deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JacksonException {
        // Expecting the JSON Object to start
        if (parser.getCurrentToken() != JsonToken.START_OBJECT) {
            throw new JsonParseException("Expected a JSON Object");
        }

        // Move to the field name (operator as the key)
        parser.nextToken();

        // Get the operator value as a String (e.g. "eq")
        String operatorValue = parser.currentName();

        // Find the conresponding enum based on the string value
        OperatorEnum operator = OperatorEnum.fromValue(operatorValue);
        if (operator == null) {
            throw new IllegalArgumentException("Unknown operator: " + operatorValue);
        }

        // Move to the field value
        parser.nextToken();
        String value = parser.getText();

        // Keep moving till the end
        while (parser.getCurrentToken() != JsonToken.END_OBJECT) {
            parser.nextToken();
        }

        return new StringFilterItem(operator, value);
    }
}
