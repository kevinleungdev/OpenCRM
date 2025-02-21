package com.opencrm.app.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.company.CompanyFilter;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.api.output.company.ContactsAggregateResponse;
import com.opencrm.app.api.output.company.DealsAggregateResponse;
import com.opencrm.app.api.output.company.NotesAggregateResponse;
import com.opencrm.app.model.Company;
import com.opencrm.app.model.Contact;
import com.opencrm.app.model.User;
import com.opencrm.app.service.company.CompanyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @QueryMapping
    public ConnectionAdapter<Company> companies(@Argument CompanyFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        Page<Company> companies = companyService.findBy(filter, sortings, paging);
        return ConnectionAdapter.from(companies);
    }

    @QueryMapping
    public Company company(@Argument Long id) {
        return companyService.findById(id).orElse(null);
    }

    @BatchMapping(typeName = "Company")
    public Map<Company, User> salesOwner(List<Company> companies) {
        Map<Company, User> results = companyService.batchFetchSalesOwners(companies);
        return results;
    }

    @BatchMapping(typeName = "Company")
    public Map<Company, ConnectionAdapter<Contact>> contacts(List<Company> companies) {
        Map<Company, List<Contact>> contactsMap = companyService.batchFetchContacts(companies);

        Map<Company, ConnectionAdapter<Contact>> results = new LinkedHashMap<>();
        companies.forEach(company -> {
            Optional<Entry<Company, List<Contact>>> contactsInCompany = contactsMap.entrySet().stream()
                    .filter(entry -> entry.getKey().getId() == company.getId()).findFirst();
            if (contactsInCompany.isPresent()) {
                results.put(company, ConnectionAdapter.from(contactsInCompany.get().getValue()));
            } else {
                results.put(company, ConnectionAdapter.from(List.of()));
            }
        });

        return results;
    }

    @BatchMapping(typeName = "Company")
    public Map<Company, List<ContactsAggregateResponse>> contactsAggregate(List<Company> companies) {
        return companyService.contactsAggregate(companies);
    }

    @BatchMapping(typeName = "Company")
    public Map<Company, List<NotesAggregateResponse>> notesAggregate(List<Company> companies) {
        return companyService.notesAggregate(companies);
    }

    @BatchMapping(typeName = "Company")
    public Map<Company, List<DealsAggregateResponse>> dealsAggregate(List<Company> companies) {
        return companyService.dealsAggregate(companies);
    }
}
