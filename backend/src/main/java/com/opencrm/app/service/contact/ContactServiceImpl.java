package com.opencrm.app.service.contact;

import org.springframework.stereotype.Service;

import com.opencrm.app.model.Contact;
import com.opencrm.app.repository.ContactRepository;
import com.opencrm.app.service.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContactServiceImpl extends BaseServiceImpl<Contact, Long, ContactRepository> implements ContactService {
    public ContactServiceImpl(ContactRepository repository) {
        super(repository);
    }
}
