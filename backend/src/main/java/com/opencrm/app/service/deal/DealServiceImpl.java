package com.opencrm.app.service.deal;

import static com.opencrm.app.api.input.deal.DealAggregateFilter.PATTERN_DEAL_AGGREGATE_GROUP_BY;
import static com.opencrm.app.api.input.deal.DealAggregateFilter.PATTERN_GROUP_BY;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.opencrm.app.api.input.deal.DealAggregateFilter;
import com.opencrm.app.api.output.deal.DealAggregateGroupBy;
import com.opencrm.app.api.output.deal.DealAggregateResponse;
import com.opencrm.app.api.output.deal.DealAggregateValue;
import com.opencrm.app.model.Deal;
import com.opencrm.app.repository.DealRepository;
import com.opencrm.app.service.BaseServiceImpl;

import graphql.schema.DataFetchingFieldSelectionSet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Selection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DealServiceImpl extends BaseServiceImpl<Deal, Long, DealRepository> implements DealService {

    private final EntityManager entityManager;

    public DealServiceImpl(DealRepository repository, EntityManager entityManager) {
        super(repository);
        this.entityManager = entityManager;
    }

    private List<String> getGroupByList(DataFetchingFieldSelectionSet selectionSet) {
        return selectionSet.getFields(PATTERN_DEAL_AGGREGATE_GROUP_BY, PATTERN_GROUP_BY)
                .stream()
                .map(field -> field.getName())
                .toList();
    }

    private List<Selection<?>> getSelectionList(Root<Deal> root, CriteriaBuilder cb,
            List<String> groupByList) {
        Path<Number> idPath = root.get(ATTR_DEAL_ID);
        Path<Number> valuePath = root.get(ATTR_DEAL_ID);

        List<Selection<?>> basicSelectionList = List.of(cb.count(idPath), cb.sum(valuePath), cb.avg(valuePath),
                cb.max(valuePath), cb.min(valuePath));

        List<Selection<?>> selectionList = new ArrayList<>(basicSelectionList);
        if (!groupByList.isEmpty()) {
            groupByList.forEach(groupby -> {
                selectionList.add(root.get(groupby));
            });
        }

        return selectionList;
    }

    @Override
    public List<DealAggregateResponse> dealAggregate(DealAggregateFilter filter,
            DataFetchingFieldSelectionSet selectionSet) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Deal> root = cq.from(Deal.class);

        if (filter != null) {
            cq.where(filter.toPredicate(root, cq, cb));
        }

        List<String> groupByList = getGroupByList(selectionSet);

        cq.multiselect(getSelectionList(root, cb, groupByList));

        if (!groupByList.isEmpty()) {
            Path<?>[] grouping = groupByList
                    .stream()
                    .map(groupBy -> root.get(groupBy))
                    .toArray(Path[]::new);

            cq.groupBy(grouping);
        }

        TypedQuery<Tuple> query = entityManager.createQuery(cq);
        List<Tuple> results = query.getResultList();

        return results.stream().map(record -> {
            DealAggregateResponse response = new DealAggregateResponse();
            response.setCount(new DealAggregateValue<>(record.get(0, Number.class)));
            response.setSum(new DealAggregateValue<>(record.get(1, Number.class)));
            response.setAvg(new DealAggregateValue<>(record.get(2, Number.class)));
            response.setMax(new DealAggregateValue<>(record.get(3, Number.class)));
            response.setMin(new DealAggregateValue<>(record.get(4, Number.class)));

            if (!groupByList.isEmpty()) {
                DealAggregateGroupBy groupBy = new DealAggregateGroupBy();

                for (int i = 0; i < groupByList.size(); i++) {
                    String groupByField = groupByList.get(i);
                    Object groupByValue = record.get(i + 5);

                    try {
                        PropertyUtils.setSimpleProperty(groupBy, groupByField, groupByValue);
                    } catch (Exception ignore) {
                        log.error("Failed to set the groupBy property", ignore);
                    }
                }

                response.setGroupBy(groupBy);
            }

            return response;
        }).toList();
    }
}
