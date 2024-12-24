package com.opencrm.app.api.output.deal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealAggregateGroupBy {
    private Integer closeDateYear;
    private Integer closeDateMonth;
    private Integer closeDateDay;
}
