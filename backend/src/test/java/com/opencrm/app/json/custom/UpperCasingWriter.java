package com.opencrm.app.json.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.opencrm.app.json.User;

public class UpperCasingWriter extends BeanPropertyWriter {

    public UpperCasingWriter(BeanPropertyWriter w) {
        super(w);
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov) throws Exception {
        String value = ((User) bean).getName();
        value = value == null ? "" : value.toUpperCase();

        jgen.writeStringField("name", value);
    }
}
