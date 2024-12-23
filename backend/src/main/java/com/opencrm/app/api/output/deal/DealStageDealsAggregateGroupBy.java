package com.opencrm.app.api.output.deal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealStageDealsAggregateGroupBy {
    private Integer closeDateYear;
    private Integer closeDateMonth;
    private Integer closeDateDay;
}
