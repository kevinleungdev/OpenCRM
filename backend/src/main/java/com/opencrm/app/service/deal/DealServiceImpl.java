package com.opencrm.app.service.deal;

import org.springframework.stereotype.Service;

import com.opencrm.app.model.Deal;
import com.opencrm.app.repository.DealRepository;
import com.opencrm.app.service.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DealServiceImpl extends BaseServiceImpl<Deal, Long, DealRepository> implements DealService {
    public DealServiceImpl(DealRepository repository) {
        super(repository);
    }
}
