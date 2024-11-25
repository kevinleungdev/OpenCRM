package com.opencrm.app.api.input.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.api.input.common.filter.FilterBase;
import com.opencrm.app.model.Event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventFilter extends FilterBase<Event> {
    private Map<OperatorEnum, String> title;
    private Map<OperatorEnum, String> description;
    private Map<OperatorEnum, LocalDate> startDate;
    private Map<OperatorEnum, LocalDateTime> endDate;
}
