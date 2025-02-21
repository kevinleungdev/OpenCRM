package com.opencrm.app.model;

import java.util.List;

import com.opencrm.app.api.output.deal.DealAggregateResponse;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "deal_stage")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealStage extends BaseEntity {
    @Column
    private String title;

    @Transient
    private List<DealAggregateResponse> dealsAggregate;

    @OneToMany(mappedBy = "stage", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    List<Deal> deals;
}
