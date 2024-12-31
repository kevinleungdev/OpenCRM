package com.opencrm.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity(name = "audit_changes")
public class AuditChange extends BaseEntity {
    @Column
    private String field;

    @Column
    private String from;

    @Column
    private String to;

    @ManyToOne
    @JoinColumn(name = "audit_id")
    private Audit audit;
}
