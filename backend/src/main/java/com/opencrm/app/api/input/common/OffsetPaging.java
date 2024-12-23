package com.opencrm.app.api.input.common;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OffsetPaging {
    private Integer limit;
    private Integer offset = 0;

    public Pageable toPageable() {
        return PageRequest.of(offset / limit, limit);
    }

    public Pageable toPageable(Sorting sorting) {
        if (sorting == null) {
            return toPageable();
        }

        return PageRequest.of(offset / limit, limit, sorting.toSort());
    }

    public Pageable toPageable(List<Sorting> sortings) {
        if (sortings == null || sortings.isEmpty()) {
            return toPageable();
        }
        return PageRequest.of(offset / limit, limit, Sort.by(sortings.stream().map(Sorting::toSortOrder).toList()));
    }
}
