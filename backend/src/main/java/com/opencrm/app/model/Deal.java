package com.opencrm.app.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "deals")
public class Deal extends BaseEntity {
    @Column
    private String title;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    @Column
    private String notes;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private DealStage stage;

    @Column
    private Integer closeDateYear;

    @Column
    private Integer closeDateMonth;

    @Column
    private Integer closeDateDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "deal_contact_id")
    private User contact;
}