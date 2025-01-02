package com.opencrm.app.api.output.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskStageTasksAggregateResponse {
    private TaskStageTasksAggregateGroupBy groupBy;
    private TaskAggregateId count;
}
