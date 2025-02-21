package com.opencrm.app.api;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.opencrm.app.api.input.audit.AuditFilter;
import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.output.ConnectionAdapter;
import com.opencrm.app.model.Audit;
import com.opencrm.app.service.audit.AuditService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @QueryMapping
    public Audit audit(@Argument Long id) {
        return auditService.findById(id).orElse(null);
    }

    @QueryMapping
    public ConnectionAdapter<Audit> audits(@Argument AuditFilter filter, @Argument List<Sorting> sortings,
            @Argument OffsetPaging paging) {
        Page<Audit> results = auditService.findBy(filter, sortings, paging);
        return ConnectionAdapter.from(results);
    }
}
