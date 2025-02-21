package com.opencrm.app.service.deal;

import java.util.List;

import com.opencrm.app.api.input.deal.DealAggregateFilter;
import com.opencrm.app.api.output.deal.DealAggregateResponse;
import com.opencrm.app.model.Deal;
import com.opencrm.app.service.BaseService;

import graphql.schema.DataFetchingFieldSelectionSet;

public interface DealService extends BaseService<Deal, Long> {

    String ATTR_DEAL_ID = "id";
    String ATTR_DEAL_VALUE = "value";

    List<DealAggregateResponse> dealAggregate(DealAggregateFilter filter, DataFetchingFieldSelectionSet selectionSet);
}
