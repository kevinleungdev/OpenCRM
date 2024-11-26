package com.opencrm.app.api.input.event;

import com.opencrm.app.api.input.common.filter.DateFilterItem;
import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.api.input.common.filter.StringFilterItem;
import com.opencrm.app.model.Event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventFilter extends Filter<Event> {
    private StringFilterItem title;
    private StringFilterItem description;
    private DateFilterItem startDate;
    private DateFilterItem endDate;
}
