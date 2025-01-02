package com.opencrm.app.api.input.task;

import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.model.TaskStage;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskStageFilter extends Filter<TaskStage> {
    private LinkedHashMap<String, Object> title;
}
