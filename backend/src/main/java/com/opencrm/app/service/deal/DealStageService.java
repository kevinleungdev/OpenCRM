package com.opencrm.app.service.deal;

import java.util.List;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.deal.DealStageFilter;
import com.opencrm.app.model.DealStage;
import com.opencrm.app.service.BaseService;

import graphql.schema.DataFetchingFieldSelectionSet;

public interface DealStageService extends BaseService<DealStage, Long> {
    List<DealStage> dealStages(DealStageFilter filter, List<Sorting> sortings, OffsetPaging paging,
            DataFetchingFieldSelectionSet selectionSet);
}
