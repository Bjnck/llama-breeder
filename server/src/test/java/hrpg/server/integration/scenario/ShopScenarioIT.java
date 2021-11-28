//package hrpg.server.integration.scenario;
//
//import hrpg.server.integration.AbstractIntegrationTest;
//import hrpg.server.item.resource.ItemRequest;
//import hrpg.server.item.type.ItemCode;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.hasItem;
//import static org.hamcrest.Matchers.lessThan;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles("shop_scenario")
//class ShopScenarioIT extends AbstractIntegrationTest {
//
//    @Test
//    @SneakyThrows
//    void coins_scenario() {
//        //check available coins
//        get(USER_URL)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.coins", equalTo(parametersProperties.getUser().getStartCoins())))
//                .andReturn();
//
//        //buy first item
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.NET).quality(1).build())
//                .andExpect(status().isCreated());
//
//        //check available coins
//        get(USER_URL)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.coins", lessThan(parametersProperties.getUser().getStartCoins())))
//                .andReturn();
//
//        //buy second item fail (insufficient coins)
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.NET).quality(1).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[*].field", hasItem("code,quality")))
//                .andExpect(jsonPath("$[*].code", hasItem("insufficientCoins")));
//    }
//
//    @Test
//    @SneakyThrows
//    void size_scenario() {
//        //buy two items
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.THIRST).quality(1).build())
//                .andExpect(status().isCreated());
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.THIRST).quality(1).build())
//                .andExpect(status().isCreated());
//
//        //buy third item fail (max size)
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.THIRST).quality(1).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[*].field", hasItem("_self")))
//                .andExpect(jsonPath("$[*].code", hasItem("maxSize")));
//    }
//
//    @Test
//    @SneakyThrows
//    void level_scenario() {
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.THIRST).quality(5).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[*].field", hasItem("quality")))
//                .andExpect(jsonPath("$[*].code", hasItem("insufficientLevel")));
//    }
//
//    @Test
//    @SneakyThrows
//    void bad_item_scenario() {
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.NET).quality(4).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[*].field", hasItem("code,quality")))
//                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")));
//    }
//}
