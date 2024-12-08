package com.opencrm.app.service.deal;

import java.util.List;

import org.springframework.stereotype.Service;

import com.opencrm.app.api.input.common.OffsetPaging;
import com.opencrm.app.api.input.common.Sorting;
import com.opencrm.app.api.input.event.DealStageFilter;
import com.opencrm.app.api.output.deals.DealStageDealsAggregateResponse;
import com.opencrm.app.api.output.deals.DealStageDealsAggregateValue;
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

        return query.getResultList()
                .stream()
                .map(record -> {
                    DealStageDealsAggregateResponse response = DealStageDealsAggregateResponse
                            .builder()
                            .count(new DealStageDealsAggregateValue(record.get(2, Integer.class)))
                            .sum(new DealStageDealsAggregateValue(record.get(3, Integer.class)))
                            .avg(new DealStageDealsAggregateValue(record.get(4, Integer.class)))
                            .min(new DealStageDealsAggregateValue(record.get(5, Integer.class)))
                            .max(new DealStageDealsAggregateValue(record.get(6, Integer.class)))
                            .build();

                    DealStage stage = DealStage
                            .builder()
                            .title(record.get(7, String.class))
                            .dealsAggregate(response)
                            .build();

                    return stage;
                }).toList();
    }
}
