package com.opencrm.app.api.output.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactsAggregateResponse {
    private ContactsAggregateGroupBy groupBy;
    private ContactsAggregateValue count;
    private ContactsAggregateValue sum;
    private ContactsAggregateValue avg;
    private ContactsAggregateValue min;
    private ContactsAggregateValue max;
}
