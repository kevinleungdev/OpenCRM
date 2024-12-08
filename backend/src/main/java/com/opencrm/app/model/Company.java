package com.opencrm.app.model;

import java.math.BigDecimal;

import com.opencrm.app.model.enums.BusinessTypeEnum;
import com.opencrm.app.model.enums.CompanySizeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "companies")
public class Company extends NameEntity {
    @Column
    private String avatarUrl;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalRevenue;

    @Column
    private String industry;

    @Column
    private CompanySizeEnum companySize;

    @Column
    private BusinessTypeEnum businessType;

    @Column
    private String website;

    @Column
    private String country;

    @OneToOne(mappedBy = "company")
    private CompanyNote note;
}
