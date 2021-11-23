//package hrpg.server.integration;
//
//import hrpg.server.item.type.ItemCode;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.CoreMatchers.hasItem;
//import static org.hamcrest.CoreMatchers.not;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class ShopEndpointIT extends AbstractIntegrationTest {
//
//    @Test
//    @SneakyThrows
//    void shop_search() {
//        //list all items
//        get(SHOP_URL)
//                .andExpect(status().isOk())
//                .andExpect(jsonPathTotalElements(33));
//
//        //list items filtered by code
//        get(SHOP_URL + "?code=" + ItemCode.NET.name())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.shop-items[*].code", not(hasItem(ItemCode.LOVE.name()))))
//                .andExpect(jsonPathTotalElements(3));
//
//        //list items filtered by quality
//        get(SHOP_URL + "?quality=" + 1)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.shop-items[*].quality", not(hasItem(2))))
//                .andExpect(jsonPathTotalElements(4));
//
//        //list items filtered by code and quality
//        get(SHOP_URL + "?" + "code=" + ItemCode.NET.name() + "&quality=" + 1)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.shop-items[*].code", not(hasItem(ItemCode.LOVE.name()))))
//                .andExpect(jsonPath("$._embedded.shop-items[*].quality", not(hasItem(2))))
//                .andExpect(jsonPathTotalElements(1));
//    }
//}
