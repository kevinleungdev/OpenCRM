package com.opencrm.app.api.input.company;

import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.model.Company;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyFilter extends Filter<Company> {
    private LinkedHashMap<String, Object> id;

    private LinkedHashMap<String, Object> name;

    private LinkedHashMap<String, Object> totalRevenue;

    private LinkedHashMap<String, Object> companySize;

    private LinkedHashMap<String, Object> industry;

    private LinkedHashMap<String, Object> businessType;

    private LinkedHashMap<String, Object> country;

    private LinkedHashMap<String, Object> website;
}
