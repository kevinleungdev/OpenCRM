package com.opencrm.app.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.opencrm.app.json.custom.MyBeanSerializerModifier;

public class JsonViewTests {

    @Test
    void whenUseJsonViewToSerialize_thenCorrect() throws JsonProcessingException {
        User user = new User(1, "Jack");

        ObjectMapper mapper = JsonMapper.builder().disable(MapperFeature.DEFAULT_VIEW_INCLUSION).build();
        String result = mapper.writerWithView(Views.Public.class).writeValueAsString(user);

        assertThat(result).contains("Jack");
        assertThat(result).doesNotContain("1");
    }

    @Test
    void whenUsePublicView_thenOnlyPublicSerialized() throws JsonProcessingException {
        Item item = new Item(1, "Book", "John");

        ObjectMapper mapper = JsonMapper.builder().build();
        String result = mapper.writerWithView(Views.Public.class).writeValueAsString(item);

        assertThat(result).contains("1");
        assertThat(result).contains("Book");
        assertThat(result).doesNotContain("John");
    }

    @Test
    void whenUseInternalView_thenAllSerialized() throws JsonProcessingException {
        Item item = new Item(1, "Book", "John");

        ObjectMapper mapper = JsonMapper.builder().build();
        String result = mapper.writerWithView(Views.Internal.class).writeValueAsString(item);

        assertThat(result).contains("1");
        assertThat(result).contains("Book");
        assertThat(result).contains("John");
    }

    @Test
    void whenUseJsonViewToDeserialize_thenCorrect() throws JsonProcessingException {
        String json = "{\"id\":1,\"name\":\"John\"}";

        ObjectMapper mapper = JsonMapper.builder().build();
        User user = mapper.readerWithView(Views.Public.class)
                .forType(User.class)
                .readValue(json);

        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getName()).isEqualTo("John");
    }

    @Test
    void whenUseCustomJsonViewToSerialize_thenCorrect() throws JsonProcessingException {
        User user = new User(1, "Jack");

        SerializerFactory serializerFactory = BeanSerializerFactory.instance
                .withSerializerModifier(new MyBeanSerializerModifier());
        ObjectMapper mapper = JsonMapper.builder()
                .serializerFactory(serializerFactory)
                .build();

        String result = mapper.writerWithView(Views.Public.class).writeValueAsString(user);
        assertThat(result).contains("1");
        assertThat(result).contains("JACK");
    }
}
