package com.opencrm.app.api.output.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DealAggregateResponse {
    private DealAggregateGroupBy groupBy;
    private DealAggregateValue<Number> count;
    private DealAggregateValue<Number> sum;
    private DealAggregateValue<Number> avg;
    private DealAggregateValue<Number> min;
    private DealAggregateValue<Number> max;
}
