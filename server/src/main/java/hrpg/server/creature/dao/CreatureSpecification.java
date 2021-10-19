package hrpg.server.creature.dao;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CreatureSpecification implements Specification<Creature> {

    private CreatureCriteria criteria;

    public CreatureSpecification(CreatureCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Creature> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria != null) {
            if (criteria.getSex() != null)
                predicates.add(builder.equal(root.get("info").get("sex"), criteria.getSex()));
            if (criteria.getGeneration() != null)
                predicates.add(builder.equal(root.get("generation"), criteria.getGeneration()));
            if (criteria.getInPen() != null) {
                if (criteria.getInPen())
                    predicates.add(builder.greaterThan(root.get("penActivationTime"), root.get("energyUpdateTime")));
                else
                    predicates.add(builder.lessThan(root.get("penActivationTime"), root.get("energyUpdateTime")));
            }
            if (criteria.getMaxMaturity() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("maturity"), criteria.getMaxMaturity()));
            if (criteria.getPregnant() != null) {
                if (criteria.getPregnant())
                    predicates.add(builder.isTrue(root.get("pregnant")));
                else
                    predicates.add(builder.isFalse(root.get("pregnant")));
            }
            if (criteria.getMinPregnancyCount() != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("breedingCount"), criteria.getMinPregnancyCount()));
            if (criteria.getIds() != null && !criteria.getIds().isEmpty()) {
                CriteriaBuilder.In<Object> inClause = builder.in(root.get("id"));
                criteria.getIds().forEach(inClause::value);
                predicates.add(inClause);
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
