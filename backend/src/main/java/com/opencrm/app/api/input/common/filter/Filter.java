package com.opencrm.app.api.input.common.filter;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.domain.Specification;

import com.opencrm.app.model.BaseEntity;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Filter<T extends BaseEntity> implements Specification<T> {

    private List<Filter<T>> and;
    private List<Filter<T>> or;

    @SuppressWarnings({ "null" })
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new LinkedList<>();

        // Get all fields (private and public modifier) of this class
        Field[] allFields = getClass().getDeclaredFields();
        // Filter only fields that are FilterItem
        List<Field> filterItems = Stream.of(allFields)
                .filter(f -> FilterItem.class.isAssignableFrom(f.getType())).toList();

        if (!filterItems.isEmpty()) {
            for (Field filterItem : filterItems) {
                String fieldName = filterItem.getName();

                try {
                    filterItem.setAccessible(true);

                    FilterItem<?> value = (FilterItem<?>) filterItem.get(this);
                    if (value == null) {
                        log.warn("Skip converting filter item '{}' to predicate as it's value is null",
                                fieldName);
                        continue;
                    }

                    Optional<Predicate> result = value.toPredicate(fieldName, root, criteriaBuilder);
                    if (result.isEmpty()) {
                        log.warn("The predicate of '{}' is empty, won't add to the criteria query!", fieldName);
                    } else {
                        predicates.add(result.get());
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    log.error("Error while getting field value!", e);
                }
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
