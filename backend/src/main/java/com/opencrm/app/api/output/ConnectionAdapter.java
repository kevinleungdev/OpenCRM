package com.opencrm.app.api.output;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionAdapter<T> {
    private List<T> nodes;
    private long totalCount;

    public static <T> ConnectionAdapter<T> from(Page<T> page) {
        List<T> nodes = page.get().collect(Collectors.toList());
        long totalCount = page.getTotalElements();

        return new ConnectionAdapter<>(nodes, totalCount);
    }

    public static <T> ConnectionAdapter<T> from(List<T> list) {
        return new ConnectionAdapter<>(list, list.size());
    }
}
