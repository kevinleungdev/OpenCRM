package com.opencrm.app.api.input.common.filter;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
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
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Filter<T extends BaseEntity> implements Specification<T> {

    private List<Filter<T>> and;
    private List<Filter<T>> or;

    private Path<?> getFieldPath(Root<T> root, Field field) {
        if (field.isAnnotationPresent(FilterItemPath.class)) {
            FilterItemPath paths = field.getAnnotation(FilterItemPath.class);

            Path<?> targetPath = root;
            for (String path : paths.value()) {
                targetPath = targetPath.get(path);
            }
            return targetPath;
        }

        return root.get(field.getName());
    }

    private Predicate handleStringScalarType(Root<T> root, CriteriaBuilder cb, Field field, OperatorEnum operator,
            String value) {
        @SuppressWarnings("unchecked")
        Path<String> fieldPath = (Path<String>) getFieldPath(root, field);

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
    private Predicate handleNumberScalarType(Root<T> root, CriteriaBuilder cb, Field field, OperatorEnum operator,
            Number value) {
        Path<Comparable> fieldPath = (Path<Comparable>) getFieldPath(root, field);

        switch (operator) {
            case EQUAL:
                return cb.equal(fieldPath, value);
            case NOT_EQUAL:
                return cb.notEqual(fieldPath, value);
            case GREATER_THAN:
                if (Comparable.class.isAssignableFrom(value.getClass())) {
                    return cb.greaterThan(fieldPath, (Comparable) value);
                }
                return null;
            case GREATER_THAN_OR_EQUAL:
                if (Comparable.class.isAssignableFrom(value.getClass())) {
                    return cb.greaterThanOrEqualTo(fieldPath, (Comparable) value);
                }
                return null;
            case LESS_THAN:
                if (Comparable.class.isAssignableFrom(value.getClass())) {
                    return cb.lessThan(fieldPath, (Comparable) value);
                }
                return null;
            case LESS_THAN_OR_EQUAL:
                if (Comparable.class.isAssignableFrom(value.getClass())) {
                    return cb.lessThanOrEqualTo(fieldPath, (Comparable) value);
                }
                return null;
            default:
                return null;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Predicate handleDateScalarType(Root<T> root, CriteriaBuilder cb, Field field, OperatorEnum operator,
            Temporal value) {
        Path<Comparable> fieldPath = (Path<Comparable>) getFieldPath(root, field);

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
    private Predicate handleCollectionScalarType(Root<T> root, CriteriaBuilder cb, Field field,
            OperatorEnum operator, Collection<?> values) {
        Path<Object> fieldPath = (Path<Object>) getFieldPath(root, field);

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
    private Predicate toPredicateInternal(Root<T> root, CriteriaBuilder cb, Field field, OperatorEnum operator,
            Object value) {
        Predicate predicate = null;

        if (value instanceof String) {
            predicate = handleStringScalarType(root, cb, field, operator, (String) value);
        } else if (Number.class.isAssignableFrom(value.getClass())) {
            predicate = handleNumberScalarType(root, cb, field, operator, (Number) value);
        } else if (value instanceof LocalDate || value instanceof LocalDateTime) {
            predicate = handleDateScalarType(root, cb, field, operator, (Temporal) value);
        } else if (Collection.class.isAssignableFrom(value.getClass())) {
            predicate = handleCollectionScalarType(root, cb, field, operator, (Collection) value);
        }

        if (predicate == null) {
            log.warn("Unable to convert the attribute '{}' to a predicate", field.getName());
        }

        return predicate;
    }

    private List<Field> getFilteringFields(Class<?> clazz) {
        // Get all fields (private and public modifier) of this class
        Field[] allFields = clazz.getDeclaredFields();

        // Filter only fields that are Map.class
        List<Field> filteringFields = new ArrayList<>(
                Stream.of(allFields).filter(f -> Map.class.isAssignableFrom(f.getType()))
                        .toList());

        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && !superClazz.equals(Object.class)) {
            filteringFields.addAll(getFilteringFields(superClazz));
        }

        return filteringFields;
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();

        List<Field> filteringFields = getFilteringFields(getClass());
        if (!filteringFields.isEmpty()) {
            for (Field filteringField : filteringFields) {
                try {
                    filteringField.setAccessible(true);

                    Map<String, ?> value = (Map<String, ?>) filteringField.get(this);
                    if (value == null || value.isEmpty()) {
                        log.debug("Skip converting filter item '{}' to predicate as it's value is null",
                                filteringField.getName());
                        continue;
                    }

                    Entry<String, ?> entry = value.entrySet().iterator().next();

                    // Operator validation
                    OperatorEnum operator = OperatorEnum.fromValue(entry.getKey());
                    if (operator == null) {
                        log.warn("Unsupported operator: {} for attribute name: {}", entry.getKey(),
                                filteringField.getName());
                        continue;
                    }

                    Predicate result = toPredicateInternal(root, criteriaBuilder, filteringField, operator,
                            entry.getValue());
                    if (result != null) {
                        predicates.add(result);
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
