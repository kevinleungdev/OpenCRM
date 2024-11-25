package com.opencrm.app.api.input.common;

import com.opencrm.app.api.input.common.enums.SortDirectionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sort {
    private String field;

    private SortDirectionEnum direction;
}
