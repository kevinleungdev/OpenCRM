package com.opencrm.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.opencrm.app.model.DealStage;

public interface DealStageRepository extends JpaRepository<DealStage, Long>, JpaSpecificationExecutor<DealStage> {
}
