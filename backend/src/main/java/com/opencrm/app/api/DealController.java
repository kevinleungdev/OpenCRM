package com.opencrm.app.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.deal.DealFilter;
import com.opencrm.app.api.input.deal.DealStageFilter;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.model.Deal;
import com.opencrm.app.model.DealStage;
import com.opencrm.app.service.deal.DealService;
import com.opencrm.app.service.deal.DealStageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;
    private final DealStageService dealStageService;

    @SchemaMapping
    @QueryMapping
    public ConnectionAdapter<Deal> deals(@Argument DealFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        Page<Deal> results = dealService.findBy(filter, sortings, paging);
        return ConnectionAdapter.from(results);
    }

    @QueryMapping
    public ConnectionAdapter<DealStage> dealStages(@Argument DealStageFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        List<DealStage> results = dealStageService.dealStages(filter, sortings, paging);
        return ConnectionAdapter.from(results);
    }
}
