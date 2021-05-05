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
                predicates.add(builder.equal(root.get("sex"), criteria.getSex()));
            if (criteria.getGeneration() != null)
                predicates.add(builder.equal(root.get("generation"), criteria.getGeneration()));

            //todo color1/color2 with join on table color for code
            //todo gene1/gene2 with join on table gene for code
            //todo wild with join on details
            //todo pregnant with join on details

            if (!predicates.isEmpty())
                return builder.and(predicates.toArray(new Predicate[0]));
        }
        return null;
    }
}
