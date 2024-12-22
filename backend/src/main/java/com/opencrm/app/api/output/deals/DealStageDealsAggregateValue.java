package com.opencrm.app.api.output.deals;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DealStageDealsAggregateValue<T> {
    private T value;
}
