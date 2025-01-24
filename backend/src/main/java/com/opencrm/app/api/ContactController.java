package com.opencrm.app.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.contact.ContactFilter;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.model.Contact;
import com.opencrm.app.service.contact.ContactService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @QueryMapping
    public ConnectionAdapter<Contact> contacts(@Argument ContactFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        Page<Contact> contacts = contactService.findBy(filter, sortings, paging);
        return ConnectionAdapter.from(contacts);
    }
}
