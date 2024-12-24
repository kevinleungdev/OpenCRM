package com.opencrm.app.api.input.deal;

import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.api.input.common.filter.FilterItemPath;
import com.opencrm.app.model.Deal;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DealFilter extends Filter<Deal> {
    private LinkedHashMap<String, Object> id;

    private LinkedHashMap<String, Object> title;

    private LinkedHashMap<String, Object> value;

    private LinkedHashMap<String, Object> closeDateYear;

    private LinkedHashMap<String, Object> closeDateMonth;

    private LinkedHashMap<String, Object> closeDateDay;

    private LinkedHashMap<String, Object> dealOwnerId;

    private LinkedHashMap<String, Object> dealContactId;

    @FilterItemPath({ "stage", "id" })
    private LinkedHashMap<String, Object> stageId;
}
