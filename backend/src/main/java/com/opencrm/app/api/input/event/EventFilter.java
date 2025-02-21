package com.opencrm.app.api.input.event;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.model.Event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventFilter extends Filter<Event> {
    private LinkedHashMap<String, String> title;
    private LinkedHashMap<String, String> description;
    private LinkedHashMap<String, LocalDate> startDate;
    private LinkedHashMap<String, LocalDate> endDate;
}
