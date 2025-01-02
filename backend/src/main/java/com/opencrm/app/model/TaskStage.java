package com.opencrm.app.model;

import java.util.List;

import com.opencrm.app.api.output.task.TaskStageTasksAggregateResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "tasks", "tasksAggregate" })
@ToString(exclude = { "tasks", "tasksAggregate" })
@Entity(name = "task_stage")
public class TaskStage extends BaseEntity {
    @Column
    private String title;

    @Transient
    private List<TaskStageTasksAggregateResponse> tasksAggregate;

    @OneToMany(mappedBy = "stage", fetch = FetchType.LAZY)
    private List<Task> tasks;
}
