package com.opencrm.app.api.input.common.filter;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.api.input.common.filter.json.DateFilterItemDeserializer;
import com.opencrm.app.api.input.common.filter.json.DateFilterItemSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@JsonSerialize(using = DateFilterItemSerializer.class)
@JsonDeserialize(using = DateFilterItemDeserializer.class)
public class DateFilterItem implements FilterItem<LocalDate> {
    private OperatorEnum operator;
    private LocalDate value;
}
