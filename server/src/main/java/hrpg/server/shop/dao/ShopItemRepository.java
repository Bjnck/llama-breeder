package hrpg.server.shop.dao;

import hrpg.server.item.type.ItemCode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShopItemRepository extends MongoRepository<ShopItem, String> {
    Optional<ShopItem> findByCodeAndQuality(ItemCode code, int quality);
}
