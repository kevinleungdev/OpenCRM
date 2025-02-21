package com.opencrm.app.service.task;

import org.springframework.stereotype.Service;

import com.opencrm.app.model.TaskStage;
import com.opencrm.app.repository.TaskStageRepository;
import com.opencrm.app.service.BaseServiceImpl;

@Service
public class TaskStageServiceImpl extends BaseServiceImpl<TaskStage, Long, TaskStageRepository>
        implements TaskStageService {

    public TaskStageServiceImpl(TaskStageRepository repository) {
        super(repository);
    }
}
