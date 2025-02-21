package com.opencrm.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.opencrm.app.model.EventCategory;

public interface EventCategoryRepository
                extends JpaRepository<EventCategory, Long>, JpaSpecificationExecutor<EventCategory> {
}
