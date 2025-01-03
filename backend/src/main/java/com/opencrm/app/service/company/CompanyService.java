package com.opencrm.app.service.company;

import java.util.List;
import java.util.Map;

import com.opencrm.app.api.output.company.DealsAggregateResponse;
import com.opencrm.app.model.Company;
import com.opencrm.app.model.User;
import com.opencrm.app.service.BaseService;

public interface CompanyService extends BaseService<Company, Long> {
    Map<Company, User> batchFetchSalesOwners(List<Company> companies);

    Map<Company, List<DealsAggregateResponse>> dealsAggregate(List<Company> companies);
}
