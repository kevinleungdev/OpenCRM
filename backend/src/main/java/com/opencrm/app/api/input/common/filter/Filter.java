package com.opencrm.app.api.input.common.filter;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.collections4.map.SingletonMap;
import org.springframework.data.jpa.domain.Specification;

import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Filter<T extends BaseEntity> implements Specification<T> {

    private List<Filter<T>> and;
    private List<Filter<T>> or;

    @SuppressWarnings({ "unchecked", "rawtype" })
    protected Predicate toPredicateInternal(Root<T> root, CriteriaBuilder cb, String field,
            OperatorEnum operator,
            Object valueToCompare) {
        if (valueToCompare != null) {
            switch (operator) {
                case EQUAL:
                    return cb.equal(root.get(field), valueToCompare);
                case NOT_EQUAL:
                    return cb.notEqual(root.get(field), valueToCompare);
                case LIKE:
                    if (valueToCompare instanceof String) {
                        return cb.like(root.get(field), "%" + valueToCompare + "%");
                    }
                    break;
                case NOT_LIKE:
                    if (valueToCompare instanceof String) {
                        return cb.notLike(root.get(field), "%" + valueToCompare + "%");
                    }
                    break;
                case GREATER_THAN:
                    if (Comparable.class.isAssignableFrom(valueToCompare.getClass())) {
                        return cb.greaterThan(root.get(field), (Comparable) valueToCompare);
                    } else {
                        log.warn("Skip {} as it is not comparable!", valueToCompare.getClass());
                    }
                    break;
                case LESS_THAN:
                    if (Comparable.class.isAssignableFrom(valueToCompare.getClass())) {
                        return cb.lessThan(root.get(field), (Comparable) valueToCompare);
                    }
                    break;
                default:
                    break;
            }
        }

        return null;
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();

        Field[] allFields = getClass().getDeclaredFields();
        if (allFields != null) {
            List<Field> filteredFields = Stream.of(allFields)
                    .filter(f -> SingletonMap.class.isAssignableFrom(f.getType())).toList();

            if (!filteredFields.isEmpty()) {
                filteredFields.forEach(f -> {
                    try {
                        f.setAccessible(true);

                        SingletonMap<OperatorEnum, Object> value = (SingletonMap<OperatorEnum, Object>) f.get(this);
                        if (value != null) {
                            OperatorEnum operator = value.getKey();
                            Object valueToCompare = value.getValue();

                            Predicate predicate = toPredicateInternal(root, criteriaBuilder, f.getName(), operator,
                                    valueToCompare);

                            if (predicate != null) {
                                predicates.add(predicate);
                            } else {
                                log.warn("Skip {} as it is not supported! Operator: {}, value: {}", f.getName(),
                                        operator,
                                        valueToCompare);
                            }
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        log.error("Error while getting field value!", e);
                    }
                });
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
