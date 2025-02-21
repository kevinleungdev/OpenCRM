package com.opencrm.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.opencrm.app.api.output.company.DealsAggregateResponse;
import com.opencrm.app.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor<Company> {

    @Query("SELECT new com.opencrm.app.api.output.company.DealsAggregateResponse("
            + "new com.opencrm.app.api.output.company.DealsAggregateGroupBy(c.id), "
            + "new com.opencrm.app.api.output.company.DealsAggregateValue(COUNT(d.id)), "
            + "new com.opencrm.app.api.output.company.DealsAggregateValue(SUM(d.value)), "
            + "new com.opencrm.app.api.output.company.DealsAggregateValue(AVG(d.value)), "
            + "new com.opencrm.app.api.output.company.DealsAggregateValue(MAX(d.value)), "
            + "new com.opencrm.app.api.output.company.DealsAggregateValue(MIN(d.value))) "
            + "FROM com.opencrm.app.model.Deal d "
            + "JOIN d.company c "
            + "GROUP BY c.id ")
    List<DealsAggregateResponse> dealsAggregateByCompany();
}
