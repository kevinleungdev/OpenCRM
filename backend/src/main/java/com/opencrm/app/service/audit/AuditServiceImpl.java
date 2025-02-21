package com.opencrm.app.service.audit;

import org.springframework.stereotype.Service;

import com.opencrm.app.model.Audit;
import com.opencrm.app.repository.AuditRepository;
import com.opencrm.app.service.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditServiceImpl extends BaseServiceImpl<Audit, Long, AuditRepository> implements AuditService {
    public AuditServiceImpl(AuditRepository repository) {
        super(repository);
    }
}
