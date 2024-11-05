package com.opencrm.app.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class DateTimeCoercing implements Coercing<LocalDateTime, String> {
    private static DateTimeFormatter createIsoFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    }

    private static LocalDateTime fromString(String input) {
        try {
            return LocalDateTime.parse(input, createIsoFormatter());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    String.format("Could not parse date from String (%s): %s", input, e.getLocalizedMessage()));
        }
    }

    @Override
    public String serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale)
            throws CoercingSerializeException {
        if (dataFetcherResult instanceof LocalDateTime) {
            return createIsoFormatter().format((LocalDateTime) dataFetcherResult);
        }
        return null;
    }

    @Override
    public LocalDateTime parseLiteral(Value<?> input, CoercedVariables variables,
            GraphQLContext graphQLContext, Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            return fromString(value);
        }

        throw new UnsupportedOperationException("Unsupported input in DateScalarType: " + input);
    }

    @Override
    public LocalDateTime parseValue(Object input, GraphQLContext graphQLContext,
            Locale locale) throws CoercingParseValueException {
        if (input instanceof LocalDateTime) {
            return (LocalDateTime) input;
        } else if (input instanceof String) {
            return fromString((String) input);
        }

        return null;
    }
}
