package com.opencrm.app.service.deal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.common.enums.OperatorEnum;
import com.opencrm.app.api.input.deal.DealAggregateFilter;
import com.opencrm.app.api.input.deal.DealStageFilter;
import com.opencrm.app.api.output.deal.DealAggregateResponse;
import com.opencrm.app.model.DealStage;
import com.opencrm.app.repository.DealStageRepository;
import com.opencrm.app.service.BaseServiceImpl;

import graphql.schema.DataFetchingFieldSelectionSet;

@Service
public class DealStageServiceImpl extends BaseServiceImpl<DealStage, Long, DealStageRepository>
        implements DealStageService {

    private final DealService dealService;

    public DealStageServiceImpl(DealStageRepository repository, DealService dealService) {
        super(repository);
        this.dealService = dealService;
    }

    @Override
    public List<DealStage> dealStages(DealStageFilter filter, List<Sorting> sortings,
            OffsetPaging paging, DataFetchingFieldSelectionSet selectionSet) {
        Page<DealStage> results = findBy(filter, sortings, paging);

        if (!results.isEmpty()) {
            List<DealStage> dealStages = results.toList();

            if (selectionSet.contains(DealAggregateFilter.PATTERN_DEAL_AGGREGATE)) {
                Map<String, List<DealStage>> dealStageGroup = dealStages.stream()
                        .collect(Collectors.groupingBy(DealStage::getTitle));

                List<DealStage> dealStageWithDealAggregateList = new ArrayList<>();
                dealStageGroup.forEach((title, dealStageList) -> {
                    DealAggregateFilter dealAggregateFilter = new DealAggregateFilter();

                    LinkedHashMap<String, Object> stageId = new LinkedHashMap<>();
                    stageId.put(OperatorEnum.IN.getValue(), dealStageList.stream().map(DealStage::getId).toList());
                    dealAggregateFilter.setStageId(stageId);

                    List<DealAggregateResponse> dealAggregateResponses = dealService.dealAggregate(dealAggregateFilter,
                            selectionSet);

                    DealStage dealStatge = DealStage.builder().title(title).dealsAggregate(dealAggregateResponses)
                            .build();
                    dealStageWithDealAggregateList.add(dealStatge);
                });

                return dealStageWithDealAggregateList;
            }
            return dealStages;
        } else {
            return List.of();
        }
    }
}
