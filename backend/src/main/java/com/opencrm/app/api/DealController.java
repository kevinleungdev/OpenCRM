package com.opencrm.app.api;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.event.DealStageFilter;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.model.DealStage;
import com.opencrm.app.service.deal.DealStageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DealController {

    private final DealStageService dealStageService;

    @QueryMapping
    public ConnectionAdapter<DealStage> dealStages(@Argument DealStageFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        List<DealStage> result = dealStageService.dealStages(filter, sortings, paging);
        return ConnectionAdapter.from(result);
    }
}
