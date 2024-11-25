package com.opencrm.app.api.input.common.filter;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.api.input.common.filter.json.StringFilterItemDeserializer;
import com.opencrm.app.api.input.common.filter.json.StringFilterItemSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@JsonSerialize(using = StringFilterItemSerializer.class)
@JsonDeserialize(using = StringFilterItemDeserializer.class)
public class StringFilterItem implements FilterItem<String> {
    private OperatorEnum operator;
    private String value;
}
