package hrpg.server.common.mongock.version_0;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import hrpg.server.item.type.ItemCode;
import hrpg.server.shop.dao.ShopItem;

@ChangeLog(order = "001")
public class Changelog_0_0_1 {

    @ChangeSet(order = "001", systemVersion = "0.0.1", id = "shopInit", author = "bjnck")
    public void shopInit(MongockTemplate mongockTemplate) {
        mongockTemplate.save(ShopItem.builder().code(ItemCode.NEST).quality(1).coins(1).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.NEST).quality(2).coins(10).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.NEST).quality(3).coins(100).build());

        mongockTemplate.save(ShopItem.builder().code(ItemCode.LOVE).quality(1).coins(1).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.LOVE).quality(2).coins(10).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.LOVE).quality(3).coins(100).build());

        mongockTemplate.save(ShopItem.builder().code(ItemCode.THIRST).quality(1).coins(1).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.THIRST).quality(2).coins(10).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.THIRST).quality(3).coins(100).build());

        mongockTemplate.save(ShopItem.builder().code(ItemCode.HUNGER).quality(1).coins(1).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.HUNGER).quality(2).coins(10).build());
        mongockTemplate.save(ShopItem.builder().code(ItemCode.HUNGER).quality(3).coins(100).build());
    }
}