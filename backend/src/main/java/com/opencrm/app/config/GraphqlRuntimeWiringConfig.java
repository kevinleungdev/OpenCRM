package com.opencrm.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;

@Configuration
public class GraphqlRuntimeWiringConfig {

    @Bean
    RuntimeWiringConfigurer defaultRuntimeWiringConfigurer() {
        return builder -> {
            addDateCoercing(builder);
        };
    }

    private void addDateCoercing(RuntimeWiring.Builder builder) {
        builder.scalar(GraphQLScalarType.newScalar()
                .name("DateTime")
                .description("A scalar that handles date and time")
                .coercing(new DateTimeCoercing())
                .build());
    }
}
