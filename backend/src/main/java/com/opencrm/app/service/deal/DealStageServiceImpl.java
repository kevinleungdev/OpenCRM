package com.opencrm.app.service.deal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.deal.DealStageFilter;
import com.opencrm.app.api.output.deal.DealStageDealsAggregateGroupBy;
import com.opencrm.app.api.output.deal.DealStageDealsAggregateResponse;
import com.opencrm.app.api.output.deal.DealStageDealsAggregateValue;
import com.opencrm.app.model.Deal;
import com.opencrm.app.model.DealStage;
import com.opencrm.app.repository.DealStageRepository;
import com.opencrm.app.service.BaseServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Service
public class DealStageServiceImpl extends BaseServiceImpl<DealStage, Long, DealStageRepository>
        implements DealStageService {

    private final EntityManager entityManager;

    public DealStageServiceImpl(DealStageRepository repository, EntityManager entityManager) {
        super(repository);
        this.entityManager = entityManager;
    }

    @Override
    public List<DealStage> dealStages(DealStageFilter filter, List<Sorting> sortings,
            OffsetPaging paging) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);

        Root<DealStage> root = cq.from(DealStage.class);
        Join<DealStage, Deal> deal = root.join("deals", JoinType.INNER);

        if (filter != null) {
            cq.where(filter.toPredicate(root, cq, cb));
        }

        cq.multiselect(
                deal.get("closeDateYear"),
                deal.get("closeDateMonth"),
                cb.count(deal.get("id")),
                cb.sum(deal.get("value")),
                cb.avg(deal.get("value")),
                cb.min(deal.get("value")),
                cb.max(deal.get("value")),
                root.get("title"))
                .groupBy(
                        deal.get("closeDateYear"),
                        deal.get("closeDateMonth"),
                        root.get("title"));

        TypedQuery<Tuple> query = entityManager.createQuery(cq);

        if (paging != null) {
            query.setFirstResult(paging.getOffset());
            query.setMaxResults(paging.getLimit());
        }

        List<Tuple> results = query.getResultList();

        Map<String, List<Tuple>> groupedResults = results.stream()
                .collect(Collectors.groupingBy(record -> record.get(7, String.class)));
        if (groupedResults.isEmpty()) {
            return List.of();
        } else {
            List<DealStage> dealStages = new ArrayList<>();

            groupedResults.forEach((title, records) -> {
                List<DealStageDealsAggregateResponse> dealAggregate = records.stream()
                        .map(record -> DealStageDealsAggregateResponse
                                .builder()
                                .groupBy(DealStageDealsAggregateGroupBy.builder()
                                        .closeDateYear(record.get(0, Integer.class))
                                        .closeDateMonth(record.get(1, Integer.class)).build())
                                .count(new DealStageDealsAggregateValue<Long>(record.get(2, Long.class)))
                                .sum(new DealStageDealsAggregateValue<BigDecimal>(record.get(3, BigDecimal.class)))
                                .avg(new DealStageDealsAggregateValue<Double>(record.get(4, Double.class)))
                                .min(new DealStageDealsAggregateValue<BigDecimal>(record.get(5, BigDecimal.class)))
                                .max(new DealStageDealsAggregateValue<BigDecimal>(record.get(6, BigDecimal.class)))
                                .build())
                        .toList();

                DealStage stage = DealStage
                        .builder()
                        .title(title)
                        .dealsAggregate(dealAggregate)
                        .build();
                dealStages.add(stage);
            });

            return dealStages;
        }
    }
}
