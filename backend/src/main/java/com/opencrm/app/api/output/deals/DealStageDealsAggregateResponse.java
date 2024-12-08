package com.opencrm.app.api.output.deals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DealStageDealsAggregateResponse {
    private DealStageDealsAggregateGroupBy groupBy;
    private DealStageDealsAggregateValue count;
    private DealStageDealsAggregateValue sum;
    private DealStageDealsAggregateValue avg;
    private DealStageDealsAggregateValue min;
    private DealStageDealsAggregateValue max;
}
