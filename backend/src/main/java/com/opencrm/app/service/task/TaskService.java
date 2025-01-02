package com.opencrm.app.service.task;

import java.util.List;
import java.util.Map;

import com.opencrm.app.api.output.task.TaskStageTasksAggregateResponse;
import com.opencrm.app.model.Task;
import com.opencrm.app.model.TaskStage;
import com.opencrm.app.service.BaseService;

public interface TaskService extends BaseService<Task, Long> {
    Map<TaskStage, List<TaskStageTasksAggregateResponse>> tasksAggregateByTaskStage(List<TaskStage> taskStages);
}
