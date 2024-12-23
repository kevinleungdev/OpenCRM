package com.opencrm.app.api.input.deal;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.model.Deal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DealFilter extends Filter<Deal> {
    private LinkedHashMap<String, String> title;
    private LinkedHashMap<String, BigDecimal> value;
    private LinkedHashMap<String, Integer> closeDateYear;
    private LinkedHashMap<String, Integer> closeDateMonth;
    private LinkedHashMap<String, Integer> closeDateDay;
}
