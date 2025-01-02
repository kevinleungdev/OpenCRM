package com.opencrm.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.opencrm.app.model.TaskStage;

public interface TaskStageRepository extends JpaRepository<TaskStage, Long>, JpaSpecificationExecutor<TaskStage> {
}
