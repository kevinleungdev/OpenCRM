package com.opencrm.app.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true, exclude = { "users", "checklist", "stage" })
@ToString(exclude = { "users", "checklist", "stage" })
@Entity(name = "tasks")
public class Task extends BaseEntity {
    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDate dueDate;

    @Column
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStage stage;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<TaskCheckListItem> checklist;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "task_assignees", joinColumns = @JoinColumn(name = "task_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
}
