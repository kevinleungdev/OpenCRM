package com.opencrm.app.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class NameEntity extends BaseEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
