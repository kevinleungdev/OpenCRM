package com.opencrm.app.service.company;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencrm.app.api.output.company.ContactsAggregateResponse;
import com.opencrm.app.api.output.company.DealsAggregateResponse;
import com.opencrm.app.api.output.company.NotesAggregateResponse;
import com.opencrm.app.model.Company;
import com.opencrm.app.model.User;
import com.opencrm.app.repository.CompanyRepository;
import com.opencrm.app.service.BaseServiceImpl;
import com.opencrm.app.service.user.UserService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl extends BaseServiceImpl<Company, Long, CompanyRepository> implements CompanyService {

    private final UserService userService;

    public CompanyServiceImpl(CompanyRepository repository, UserService userService) {
        super(repository);
        this.userService = userService;
    }

    @Override
    public Map<Company, User> batchFetchSalesOwners(List<Company> companies) {
        List<Long> salesOwnerIds = companies.stream().map(Company::getSalesOwnerId).toList();

        List<User> salesOwners = userService.findBy((root, cq, cb) -> {
            In<Long> inClause = cb.in(root.get("id"));

            for (var id : salesOwnerIds) {
                inClause.value(id);
            }

            return inClause;
        }, q -> q.all());

        return salesOwners.stream()
                .collect(Collectors.toMap(
                        salesOwner -> companies.stream()
                                .filter(company -> company.getSalesOwnerId() == salesOwner.getId()).findFirst()
                                .get(),
                        salesOwner -> salesOwner));
    }

    @Override
    public Map<Company, List<DealsAggregateResponse>> dealsAggregate(List<Company> companies) {
        CompanyRepository repo = (CompanyRepository) getRepository();

        List<DealsAggregateResponse> results = repo.dealsAggregateByCompany();
        Map<Long, List<DealsAggregateResponse>> groupingResults = results.stream()
                .collect(Collectors.groupingBy(r -> r.getGroupBy().getCompanyId()));

        Map<Company, List<DealsAggregateResponse>> dealsAggregate = new LinkedHashMap<>();
        groupingResults.forEach((companyId, dealAggregateList) -> {
            Company company = companies.stream().filter(c -> companyId == c.getId()).findFirst().orElse(null);
            if (company != null) {
                dealsAggregate.put(company, dealAggregateList);
            }
        });
        return dealsAggregate;
    }

    @Override
    public Map<Company, List<ContactsAggregateResponse>> contactsAggregate(List<Company> companies) {
        // Only return an empty map for now
        Map<Company, List<ContactsAggregateResponse>> contactsAggregate = new LinkedHashMap<>();
        companies.forEach(company -> contactsAggregate.put(company, List.of()));

        return contactsAggregate;
    }

    @Override
    public Map<Company, List<NotesAggregateResponse>> notesAggregate(List<Company> companies) {
        // Only return an empty map for now
        Map<Company, List<NotesAggregateResponse>> notesAggregate = new LinkedHashMap<>();
        companies.forEach(company -> notesAggregate.put(company, List.of()));

        return notesAggregate;
    }
}
