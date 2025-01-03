package com.opencrm.app.model;

import java.math.BigDecimal;
import java.util.List;

import com.opencrm.app.api.output.company.DealsAggregateResponse;
import com.opencrm.app.model.enums.BusinessTypeEnum;
import com.opencrm.app.model.enums.CompanySizeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
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
    @Enumerated(EnumType.STRING)
    private CompanySizeEnum companySize;

    @Column
    @Enumerated(EnumType.STRING)
    private BusinessTypeEnum businessType;

    @Column
    private String website;

    @Column
    private String country;

    @Column
    private Long salesOwnerId;

    @Transient
    private User salesOwner;

    @Transient
    private List<DealsAggregateResponse> dealsAggregate;
}
