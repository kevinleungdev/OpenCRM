package com.opencrm.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "company_notes")
public class CompanyNote extends BaseEntity {
    @Column
    private String note;

    @OneToOne
    private Company company;
}
