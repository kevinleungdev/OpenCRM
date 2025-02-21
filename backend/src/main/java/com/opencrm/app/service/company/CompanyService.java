package com.opencrm.app.service.company;

import java.util.List;
import java.util.Map;

import com.opencrm.app.api.output.company.ContactsAggregateResponse;
import com.opencrm.app.api.output.company.DealsAggregateResponse;
import com.opencrm.app.api.output.company.NotesAggregateResponse;
import com.opencrm.app.model.Company;
import com.opencrm.app.model.Contact;
import com.opencrm.app.model.User;
import com.opencrm.app.service.BaseService;

public interface CompanyService extends BaseService<Company, Long> {
    Map<Company, User> batchFetchSalesOwners(List<Company> companies);

    Map<Company, List<Contact>> batchFetchContacts(List<Company> companies);

    Map<Company, List<DealsAggregateResponse>> dealsAggregate(List<Company> companies);

    Map<Company, List<ContactsAggregateResponse>> contactsAggregate(List<Company> companies);

    Map<Company, List<NotesAggregateResponse>> notesAggregate(List<Company> companies);
}
