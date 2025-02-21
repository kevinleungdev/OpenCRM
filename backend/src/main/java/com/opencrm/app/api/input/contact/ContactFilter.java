package com.opencrm.app.api.input.contact;

import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.model.Contact;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContactFilter extends Filter<Contact> {
    private LinkedHashMap<String, Object> id;
}
