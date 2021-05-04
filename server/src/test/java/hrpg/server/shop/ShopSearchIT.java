package hrpg.server.shop;

import hrpg.server.AbstractIntegrationTest;
import hrpg.server.item.type.ItemCode;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ShopSearchIT extends AbstractIntegrationTest {

    private static final String SHOP_URL = "/shop-items";

    @Test
    void listAllItems() throws Exception {
        get(SHOP_URL)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(33));
    }

    @Test
    void listItemsFilteredByCode() throws Exception {
        get(SHOP_URL + "?code=" + ItemCode.NEST.name())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].code", not(hasItem(ItemCode.LOVE.name()))))
                .andExpect(jsonPathTotalElements(3));
    }

    @Test
    void listItemsFilteredByQuality() throws Exception {
        get(SHOP_URL + "?quality=" + 1)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].quality", not(hasItem(2))))
                .andExpect(jsonPathTotalElements(4));
    }

    @Test
    void listItemsFilteredByCodeAndQuality() throws Exception {
        get(SHOP_URL + "?" + "code=" + ItemCode.NEST.name() + "&quality=" + 1)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].code", not(hasItem(ItemCode.LOVE.name()))))
                .andExpect(jsonPath("$._embedded.items[*].quality", not(hasItem(2))))
                .andExpect(jsonPathTotalElements(1));
    }
}
