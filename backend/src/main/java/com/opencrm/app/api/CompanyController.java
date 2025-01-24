package com.opencrm.app.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.company.CompanyFilter;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.api.output.company.DealsAggregateResponse;
import com.opencrm.app.model.Company;
import com.opencrm.app.model.User;
import com.opencrm.app.service.company.CompanyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @QueryMapping
    public ConnectionAdapter<Company> companies(@Argument CompanyFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        Page<Company> companies = companyService.findBy(filter, sortings, paging);
        return ConnectionAdapter.from(companies);
    }

    @QueryMapping
    public Company company(@Argument Long id) {
        return companyService.findById(id).orElse(null);
    }

    @BatchMapping(typeName = "Company")
    public Map<Company, User> salesOwner(List<Company> companies) {
        return companyService.batchFetchSalesOwners(companies);
    }

    @BatchMapping(typeName = "Company")
    public Map<Company, List<DealsAggregateResponse>> dealsAggregate(List<Company> companies) {
        return companyService.dealsAggregate(companies);
    }
}
