package com.opencrm.app.api.input.common.filter;

import com.opencrm.app.api.input.common.enums.OperatorEnum;

public interface FilterItem<T> {
    OperatorEnum getOperator();

    T getValue();

    default boolean supported() {
        return true;
    }
}
