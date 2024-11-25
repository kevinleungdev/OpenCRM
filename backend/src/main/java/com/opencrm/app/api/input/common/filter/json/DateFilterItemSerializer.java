package com.opencrm.app.api.input.common.filter.json;

import static com.opencrm.app.utils.Constants.UNIFIED_DATE_FORMAT;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.opencrm.app.api.input.common.filter.DateFilterItem;

public class DateFilterItemSerializer extends JsonSerializer<DateFilterItem> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(UNIFIED_DATE_FORMAT);

    @Override
    public void serialize(DateFilterItem item, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        // Start writing an JSON Object
        gen.writeStartObject();

        gen.writeFieldName(item.getOperator().getValue());
        gen.writeString(FORMATTER.format(item.getValue()));

        // End JSON Object
        gen.writeEndObject();
    }
}
