package com.opencrm.app.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.event.EventFilter;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.model.Event;
import com.opencrm.app.service.event.EventService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class EventController {

    private final EventService eventService;

    @QueryMapping
    public ConnectionAdapter<Event> events(@Argument EventFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        Page<Event> result = eventService.findBy(filter, sortings, paging);
        return ConnectionAdapter.from(result);
    }
}
