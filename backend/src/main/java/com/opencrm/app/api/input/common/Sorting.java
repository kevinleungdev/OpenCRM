package com.opencrm.app.api.input.common;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.opencrm.app.api.input.common.enums.SortDirectionEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sorting {
    private String field;
    private SortDirectionEnum direction;

    public static List<Sort.Order> defaultOrders() {
        return List.of(Sort.Order.asc("id"));
    }

    public Sort toSort() {
        return Sort.by(direction == SortDirectionEnum.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, field);
    }

    public Sort.Order toSortOrder() {
        return new Sort.Order(direction == SortDirectionEnum.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, field);
    }
}
