package hrpg.server.shop.dao;

import hrpg.server.item.type.ItemCode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface ShopItemRepository extends PagingAndSortingRepository<ShopItem, Integer>, JpaSpecificationExecutor<ShopItem> {
    Optional<ShopItem> findByCodeAndQuality(@NotNull ItemCode code, int quality);
}
