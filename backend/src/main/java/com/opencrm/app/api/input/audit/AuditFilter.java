package com.opencrm.app.api.input.audit;

import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.model.Audit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuditFilter extends Filter<Audit> {
    private LinkedHashMap<String, Object> id;
    private LinkedHashMap<String, Object> targetId;
    private LinkedHashMap<String, Object> targetEntity;
    private LinkedHashMap<String, Object> action;
}