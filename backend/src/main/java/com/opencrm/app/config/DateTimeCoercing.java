package com.opencrm.app.config;

import static com.opencrm.app.utils.Constants.ISO_DATE_TIME_FORMAT;
import static com.opencrm.app.utils.Constants.SHORT_DATE_FORMAT;
import static com.opencrm.app.utils.Constants.SHORT_DATE_TIME_FORMAT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.util.Locale;
import java.util.regex.Pattern;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class DateTimeCoercing implements Coercing<Temporal, String> {

    private static final String VALID_DATE_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
    private static final String VALID_DATE_TIME_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s(0\\d|1\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
    private static final String VALID_ISO_DATE_TIME_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])T(0\\d|1\\d|2[0-3]):([0-5]\\d):([0-5]\\d)\\.\\d{3}Z$";

    private static DateTimeFormatter createIsoDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(ISO_DATE_TIME_FORMAT);
    }

    private static DateTimeFormatter createShortDateFormatter() {
        return DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT);
    }

    private static DateTimeFormatter createShortDateTimeFormat() {
        return DateTimeFormatter.ofPattern(SHORT_DATE_TIME_FORMAT);
    }

    private static Temporal fromString(String input) {
        try {
            if (Pattern.matches(VALID_DATE_PATTERN, input)) {
                return LocalDate.parse(input, createShortDateFormatter());
            } else if (Pattern.matches(VALID_DATE_TIME_PATTERN, input)) {
                return LocalDateTime.parse(input, createShortDateTimeFormat());
            } else if (Pattern.matches(VALID_ISO_DATE_TIME_PATTERN, input)) {
                return LocalDateTime.parse(input, createIsoDateTimeFormatter());
            } else {
                throw new IllegalArgumentException(
                        String.format("Unknow date format (%s)", input));
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    String.format("Could not parse date from String (%s): %s", input, e.getLocalizedMessage()));
        }
    }

    @Override
    public String serialize(Object dataFetcherResult, GraphQLContext graphQLContext, Locale locale)
            throws CoercingSerializeException {
        if (dataFetcherResult instanceof LocalDate) {
            return createShortDateFormatter().format((LocalDate) dataFetcherResult);
        } else if (dataFetcherResult instanceof LocalDateTime) {
            return createShortDateFormatter().format((LocalDateTime) dataFetcherResult);
        }
        return null;
    }

    @Override
    public Temporal parseLiteral(Value<?> input, CoercedVariables variables,
            GraphQLContext graphQLContext, Locale locale) throws CoercingParseLiteralException {
        if (input instanceof StringValue) {
            String value = ((StringValue) input).getValue();
            return fromString(value);
        }

        throw new UnsupportedOperationException("Unsupported input in DateScalarType: " + input);
    }

    @Override
    public Temporal parseValue(Object input, GraphQLContext graphQLContext,
            Locale locale) throws CoercingParseValueException {
        if (input instanceof LocalDateTime) {
            return (LocalDateTime) input;
        } else if (input instanceof String) {
            return fromString((String) input);
        }

        return null;
    }
}
