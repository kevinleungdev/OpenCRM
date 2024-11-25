package com.opencrm.app.api.input.common.filter.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.opencrm.app.api.input.common.filter.StringFilterItem;

public class StringFilterItemSerializer extends JsonSerializer<StringFilterItem> {
    @Override
    public void serialize(StringFilterItem item, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        // Start writing an JSON Object
        gen.writeStartObject();

        gen.writeFieldName(item.getOperator().getValue());
        gen.writeString(item.getValue());

        // End JSON Object
        gen.writeEndObject();
    }
}
