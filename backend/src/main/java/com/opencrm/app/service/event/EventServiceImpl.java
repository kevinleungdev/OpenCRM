package com.opencrm.app.service.event;

import org.springframework.stereotype.Service;

import com.opencrm.app.model.Event;
import com.opencrm.app.repository.EventRepository;
import com.opencrm.app.service.BaseServiceImpl;

@Service
public class EventServiceImpl extends BaseServiceImpl<Event, Long, EventRepository> implements EventService {

    public EventServiceImpl(EventRepository repository) {
        super(repository);
    }
}
