package com.opencrm.app.api.output.deal;

import java.math.BigDecimal;

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
    private DealStageDealsAggregateValue<Long> count;
    private DealStageDealsAggregateValue<BigDecimal> sum;
    private DealStageDealsAggregateValue<Double> avg;
    private DealStageDealsAggregateValue<BigDecimal> min;
    private DealStageDealsAggregateValue<BigDecimal> max;
}
