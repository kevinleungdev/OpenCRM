package com.opencrm.app.service.task;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencrm.app.api.output.task.TaskStageTasksAggregateResponse;
import com.opencrm.app.model.Task;
import com.opencrm.app.model.TaskStage;
import com.opencrm.app.repository.TaskRepository;
import com.opencrm.app.service.BaseServiceImpl;

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, Long, TaskRepository> implements TaskService {
    public TaskServiceImpl(TaskRepository repository) {
        super(repository);
    }

    @Override
    public Map<TaskStage, List<TaskStageTasksAggregateResponse>> tasksAggregateByTaskStage(
            List<TaskStage> taskStages) {
        List<String> titles = taskStages.stream().map(TaskStage::getTitle).toList();

        TaskRepository repo = (TaskRepository) getRepository();
        List<TaskStageTasksAggregateResponse> responses = repo.tasksAggregateByTaskStageTitle(titles);

        Map<String, List<TaskStageTasksAggregateResponse>> groupingResult = responses.stream()
                .collect(
                        Collectors.groupingBy(res -> res.getGroupBy().getTitle(), Collectors.toList()));

        Map<TaskStage, List<TaskStageTasksAggregateResponse>> result = new LinkedHashMap<>();
        groupingResult.forEach((title, res) -> {
            TaskStage taskStage = taskStages.stream().filter(stage -> stage.getTitle().equals(title)).findFirst().get();
            result.put(taskStage, res);
        });

        return result;
    }
}
