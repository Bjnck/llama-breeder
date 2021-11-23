//package hrpg.server.integration;
//
//import hrpg.server.item.resource.ItemRequest;
//import hrpg.server.item.type.ItemCode;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpHeaders;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class ItemEndpointIT extends AbstractIntegrationTest {
//
//    @Test
//    @SneakyThrows
//    void item_endpoints() {
//        //get all items found 0
//        get(ITEM_URL)
//                .andExpect(status().isOk())
//                .andExpect(jsonPathTotalElements(0));
//
//        //create item
//        String location = post(ITEM_URL, ItemRequest.builder().code(ItemCode.NET).quality(2).build())
//                .andExpect(status().isCreated())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//
//        //get item
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", equalTo(location.substring(location.lastIndexOf("/")+1))))
//                .andExpect(jsonPath("$.code", equalTo(ItemCode.NET.name())))
//                .andExpect(jsonPath("$.quality", equalTo(2)))
//                .andExpect(jsonPath("$.life", equalTo(100)))
//                .andExpect(jsonPath("$._links.self.href", equalTo(location)));
//
//        //get all items found 1
//        get(ITEM_URL)
//                .andExpect(status().isOk())
//                .andExpect(jsonPathTotalElements(1))
//                .andExpect(jsonPath("$._embedded.items[*].id", hasItem(location.substring(location.lastIndexOf("/")+1))))
//                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.NET.name())))
//                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(2)))
//                .andExpect(jsonPath("$._embedded.items[*].life", hasItem(100)))
//                .andExpect(jsonPath("$._embedded.items[*]._links.self.href", hasItem(location)));
//
//        //delete item
//        delete(location)
//                .andExpect(status().isNoContent());
//        get(location)
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @SneakyThrows
//    void item_search() {
//        //create 2 items
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.THIRST).quality(1).build())
//                .andExpect(status().isCreated());
//        post(ITEM_URL, ItemRequest.builder().code(ItemCode.HUNGER).quality(2).build())
//                .andExpect(status().isCreated());
//
//        //list all items
//        get(ITEM_URL)
//                .andExpect(status().isOk())
//                .andExpect(jsonPathTotalElements(2));
//
//        //list items filtered by code
//        get(ITEM_URL + "?code=" + ItemCode.THIRST.name())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.THIRST.name())))
//                .andExpect(jsonPathTotalElements(1));
//
//        //list items filtered by quality
//        get(ITEM_URL + "?quality=" + 1)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(1)))
//                .andExpect(jsonPathTotalElements(1));
//
//        //list items filtered by code and quality
//        get(ITEM_URL + "?" + "code=" + ItemCode.THIRST.name() + "&quality=" + 1)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.THIRST.name())))
//                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(1)))
//                .andExpect(jsonPathTotalElements(1));
//
//        //list items ordered by id asc, receive last created
//        get(ITEM_URL + "?" + "sort=id,asc" + "&size=1")
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.THIRST.name())))
//                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(1)));
//    }
//}
