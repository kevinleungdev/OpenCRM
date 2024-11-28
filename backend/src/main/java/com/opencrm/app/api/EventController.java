package com.opencrm.app.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sort;
import com.opencrm.app.api.input.event.EventFilter;
import com.opencrm.app.model.Event;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class EventController {
    @QueryMapping
    public Page<Event> events(@Argument EventFilter filter, @Argument List<Sort> sorting,
            @Argument OffsetPaging paging) {
        log.info("filter: {}, sorters: {}, paging: {}", filter, sorting, paging);
        return null;
    }
}
