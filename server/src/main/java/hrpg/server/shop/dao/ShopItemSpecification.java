package hrpg.server.shop.dao;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ShopItemSpecification implements Specification<ShopItem> {

    private ShopItemCriteria criteria;

    public ShopItemSpecification(ShopItemCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<ShopItem> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria != null) {
            if (criteria.getCode() != null)
                predicates.add(builder.equal(root.get("code"), criteria.getCode()));
            if (criteria.getQuality() != null)
                predicates.add(builder.equal(root.get("quality"), criteria.getQuality()));

            if (!predicates.isEmpty())
                return builder.and(predicates.toArray(new Predicate[0]));
        }
        return null;
    }
}
