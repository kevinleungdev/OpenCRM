package com.opencrm.app.json;

import java.time.LocalDate;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.json.serialize.DateFilterItemDeserializer;
import com.opencrm.app.json.serialize.DateFilterItemSerializer;
import com.opencrm.app.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@JsonSerialize(using = DateFilterItemSerializer.class)
@JsonDeserialize(using = DateFilterItemDeserializer.class)
@Slf4j
public class DateFilterItem implements FilterItem<LocalDate> {
    private OperatorEnum operator;
    private LocalDate value;

    @Override
    public Optional<Predicate> toPredicate(String fieldName, Root<? extends BaseEntity> root, CriteriaBuilder cb) {
        Path<LocalDate> path = root.get(fieldName);

        switch (operator) {
            case EQUAL:
                return Optional.of(cb.equal(path, value));
            case NOT_EQUAL:
                return Optional.of(cb.notEqual(path, value));
            case GREATER_THAN:
                return Optional.of(cb.greaterThan(path, value));
            case LESS_THAN:
                return Optional.of(cb.lessThan(path, value));
            case GREATER_THAN_OR_EQUAL:
                return Optional.of(cb.greaterThanOrEqualTo(path, value));
            case LESS_THAN_OR_EQUAL:
                return Optional.of(cb.lessThanOrEqualTo(path, value));
            default:
                log.warn("Unsupported operator: {} for attribute name: {}", operator, fieldName);
                return Optional.empty();
        }
    }
}
