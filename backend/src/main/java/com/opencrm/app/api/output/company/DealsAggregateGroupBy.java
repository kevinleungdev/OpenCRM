package com.opencrm.app.api.output.company;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealsAggregateGroupBy {
    private Long id;
    private String title;
    private BigDecimal value;
    private Integer closeDateYear;
    private Integer closeDateMonth;
    private Integer closeDateDay;
    private Long companyId;

    public DealsAggregateGroupBy(Long companyId) {
        this.companyId = companyId;
    }
}
