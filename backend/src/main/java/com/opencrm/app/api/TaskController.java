package com.opencrm.app.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.task.TaskStageFilter;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.api.output.task.TaskStageTasksAggregateResponse;
import com.opencrm.app.model.TaskStage;
import com.opencrm.app.service.task.TaskService;
import com.opencrm.app.service.task.TaskStageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskStageService taskStageService;

    @QueryMapping
    public ConnectionAdapter<TaskStage> taskStages(@Argument TaskStageFilter filter,
            @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        Page<TaskStage> results = taskStageService.findBy(filter, sortings, paging);
        return ConnectionAdapter.from(results);
    }

    @BatchMapping(typeName = "TaskStage")
    public Map<TaskStage, List<TaskStageTasksAggregateResponse>> tasksAggregate(List<TaskStage> taskStages) {
        return taskService.tasksAggregateByTaskStage(taskStages);
    }
}
