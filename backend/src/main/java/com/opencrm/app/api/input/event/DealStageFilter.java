package com.opencrm.app.api.input.event;

import java.util.Collection;
import java.util.LinkedHashMap;

import com.opencrm.app.api.input.common.filter.Filter;
import com.opencrm.app.model.DealStage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class DealStageFilter extends Filter<DealStage> {
    private LinkedHashMap<String, Collection<String>> title;
}
