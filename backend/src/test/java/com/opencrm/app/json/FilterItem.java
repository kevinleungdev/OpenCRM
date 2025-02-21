package com.opencrm.app.json;

import java.util.Optional;

import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface FilterItem<T> {
    OperatorEnum getOperator();

    T getValue();

    default Optional<Predicate> toPredicate(String fieldName, Root<? extends BaseEntity> root, CriteriaBuilder cb) {
        return Optional.empty();
    }
}
