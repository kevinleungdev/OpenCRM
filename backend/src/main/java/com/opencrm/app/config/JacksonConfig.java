package com.opencrm.app.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {
    /**
     * Apply additional customizations to the Jackson ObjectMapper.
     *
     * @return a Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    Jackson2ObjectMapperBuilderCustomizer defaultJackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // Register the JavaTimeModule to support serialization and deserialization of
            // Java 8 date and time objects
            builder.modulesToInstall(new JavaTimeModule());
        };
    }
}
