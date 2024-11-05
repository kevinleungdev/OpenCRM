package com.opencrm.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.opencrm.app.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
