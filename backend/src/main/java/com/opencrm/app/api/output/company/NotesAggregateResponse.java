package com.opencrm.app.api.output.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotesAggregateResponse {
    private NotesAggregateGroupBy groupBy;
    private NotesAggregateValue count;
    private NotesAggregateValue sum;
    private NotesAggregateValue avg;
    private NotesAggregateValue min;
    private NotesAggregateValue max;
}
