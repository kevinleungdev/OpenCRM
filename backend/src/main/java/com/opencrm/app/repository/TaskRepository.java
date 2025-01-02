package com.opencrm.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.opencrm.app.api.output.task.TaskStageTasksAggregateResponse;
import com.opencrm.app.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {

    @Query("SELECT new com.opencrm.app.api.output.task.TaskStageTasksAggregateResponse(new com.opencrm.app.api.output.task.TaskStageTasksAggregateGroupBy(s.title), new com.opencrm.app.api.output.task.TaskAggregateId(COUNT(t.id))) "
            +
            "FROM com.opencrm.app.model.Task t JOIN t.stage s WHERE s.title IN :taskStageTitles GROUP BY s.title")
    List<TaskStageTasksAggregateResponse> tasksAggregateByTaskStageTitle(
            @Param("taskStageTitles") List<String> taskStageTitles);
}
