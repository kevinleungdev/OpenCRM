package com.opencrm.app.api.output.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealsAggregateResponse {
    private DealsAggregateGroupBy groupBy;
    private DealsAggregateValue count;
    private DealsAggregateValue sum;
    private DealsAggregateValue avg;
    private DealsAggregateValue max;
    private DealsAggregateValue min;
}
