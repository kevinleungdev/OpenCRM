package com.opencrm.app.json;

import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.json.serialize.StringFilterItemDeserializer;
import com.opencrm.app.json.serialize.StringFilterItemSerializer;
import com.opencrm.app.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@JsonSerialize(using = StringFilterItemSerializer.class)
@JsonDeserialize(using = StringFilterItemDeserializer.class)
@Slf4j
public class StringFilterItem implements FilterItem<String> {
    private OperatorEnum operator;
    private String value;

    @Override
    public Optional<Predicate> toPredicate(String fieldName, Root<? extends BaseEntity> root, CriteriaBuilder cb) {
        Path<String> fieldPath = root.get(fieldName);

        switch (operator) {
            case EQUAL:
                return Optional.of(cb.equal(fieldPath, value));
            case NOT_EQUAL:
                return Optional.of(cb.notEqual(fieldPath, value));
            case LIKE:
                return Optional.of(cb.like(fieldPath, "%" + value + "%"));
            case NOT_LIKE:
                return Optional.of(cb.notLike(fieldPath, "%" + value + "%"));
            default:
                log.warn("Unsupported operator: {} for attribute name: {}", operator, fieldName);
                return Optional.empty();
        }
    }
}
