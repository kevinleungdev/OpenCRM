package com.opencrm.app.api.input.common.filter;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;

import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Filter<T extends BaseEntity> implements Specification<T> {

    private List<Filter<T>> and;
    private List<Filter<T>> or;

    private Predicate handleStringScalarType(Root<T> root, CriteriaBuilder cb, String fieldName, OperatorEnum operator,
            String value) {
        Path<String> fieldPath = root.get(fieldName);
        switch (operator) {
            case EQUAL:
                return cb.equal(fieldPath, value);
            case NOT_EQUAL:
                return cb.notEqual(fieldPath, value);
            case LIKE:
                return cb.like(fieldPath, "%" + value + "%");
            case NOT_LIKE:
                return cb.notLike(fieldPath, "%" + value + "%");
            default:
                return null;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Predicate handleDateScalarType(Root<T> root, CriteriaBuilder cb, String fieldName, OperatorEnum operator,
            Temporal value) {
        Path fieldPath = root.get(fieldName);

        switch (operator) {
            case EQUAL:
                return cb.equal(fieldPath, value);
            case NOT_EQUAL:
                return cb.notEqual(fieldPath, value);
            case GREATER_THAN:
                return cb.greaterThan(fieldPath, (Comparable) value);
            case GREATER_THAN_OR_EQUAL:
                return cb.greaterThanOrEqualTo(fieldPath, (Comparable) value);
            case LESS_THAN:
                return cb.lessThan(fieldPath, (Comparable) value);
            case LESS_THAN_OR_EQUAL:
                return cb.lessThanOrEqualTo(fieldPath, (Comparable) value);
            default:
                return null;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Predicate handleCollectionScalarType(Root<T> root, CriteriaBuilder cb, String fieldName,
            OperatorEnum operator, Collection<?> values) {
        Path fieldPath = root.get(fieldName);

        switch (operator) {
            case IN:
            case NOT_IN:
                In inClause = cb.in(fieldPath);
                for (Object value : values) {
                    inClause.value(value);
                }

                if (operator == OperatorEnum.IN) {
                    return inClause;
                } else {
                    return cb.not(inClause);
                }
            default:
                return null;
        }
    }

    @SuppressWarnings("rawtypes")
    protected Predicate toPredicateInternal(Root<T> root, CriteriaBuilder cb, String fieldName, OperatorEnum operator,
            Object value) {
        Predicate predicate = null;

        if (value instanceof String) {
            predicate = handleStringScalarType(root, cb, fieldName, operator, (String) value);
        } else if (value instanceof LocalDate || value instanceof LocalDateTime) {
            predicate = handleDateScalarType(root, cb, fieldName, operator, (Temporal) value);
        } else if (Collection.class.isAssignableFrom(value.getClass())) {
            predicate = handleCollectionScalarType(root, cb, fieldName, operator, (Collection) value);
        }

        if (predicate == null) {
            log.warn("Unable to convert the attribute '{}' to a predicate", fieldName);
        }

        return predicate;
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();

        // Get all fields (private and public modifier) of this class
        Field[] allFields = getClass().getDeclaredFields();
        // Filter only fields that are Map.class
        List<Field> filteringFields = Stream.of(allFields)
                .filter(f -> Map.class.isAssignableFrom(f.getType())).toList();

        if (!filteringFields.isEmpty()) {
            for (Field filteringField : filteringFields) {
                String fieldName = filteringField.getName();

                try {
                    filteringField.setAccessible(true);

                    Map<String, ?> value = (Map<String, ?>) filteringField.get(this);
                    if (value == null || value.isEmpty()) {
                        log.debug("Skip converting filter item '{}' to predicate as it's value is null",
                                fieldName);
                        continue;
                    }

                    Entry<String, ?> entry = value.entrySet().iterator().next();

                    // Operator validation
                    OperatorEnum operator = OperatorEnum.fromValue(entry.getKey());
                    if (operator == null) {
                        log.warn("Unsupported operator: {} for attribute name: {}", entry.getKey(),
                                fieldName);
                        continue;
                    }

                    Predicate result = toPredicateInternal(root, criteriaBuilder, fieldName, operator,
                            entry.getValue());
                    if (result != null) {
                        predicates.add(result);
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Error while getting field value!", e);
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
