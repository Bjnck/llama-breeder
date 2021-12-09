package hrpg.server.item.dao;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ItemSpecification implements Specification<Item> {

    private ItemCriteria criteria;

    public ItemSpecification(ItemCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria != null) {
            if (criteria.getCode() != null)
                predicates.add(builder.equal(root.get("code"), criteria.getCode()));
            if (criteria.getQuality() != null)
                predicates.add(builder.equal(root.get("quality"), criteria.getQuality()));
            //todo join table with penItem
//            if (criteria.getInPen() != null) {
//                if (criteria.getInPen())
//                    predicates.add(builder.greaterThan(root.get("penActivationTime"), root.get("energyUpdateTime")));
//                else
//                    predicates.add(builder.lessThan(root.get("penActivationTime"), root.get("energyUpdateTime")));
//            }
            if (criteria.getMaxLife() != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("life"), criteria.getMaxLife()));

            if (!predicates.isEmpty())
                return builder.and(predicates.toArray(new Predicate[0]));
        }
        return null;
    }
}
