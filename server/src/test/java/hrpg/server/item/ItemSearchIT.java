package hrpg.server.item;

import hrpg.server.AbstractIntegrationTest;
import hrpg.server.item.resource.ItemRequest;
import hrpg.server.item.type.ItemCode;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemSearchIT extends AbstractIntegrationTest {

    private static final String ITEM_URL = "/items";

    @Test
    void listAllItems() throws Exception {
        //create 2 items
        post(ITEM_URL, ItemRequest.builder().code(ItemCode.THIRST).quality(1).build())
                .andDo(print())
                .andExpect(status().isCreated());
        post(ITEM_URL, ItemRequest.builder().code(ItemCode.HUNGER).quality(2).build())
                .andDo(print())
                .andExpect(status().isCreated());

        //list all items
        get(ITEM_URL)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(2));

        //list items filtered by code
        get(ITEM_URL + "?code=" + ItemCode.THIRST.name())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.THIRST.name())))
                .andExpect(jsonPathTotalElements(1));

        //list items filtered by quality
        get(ITEM_URL + "?quality=" + 1)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(1)))
                .andExpect(jsonPathTotalElements(1));

        //list items filtered by code and quality
        get(ITEM_URL + "?" + "code=" + ItemCode.THIRST.name() + "&quality=" + 1)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.THIRST.name())))
                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(1)))
                .andExpect(jsonPathTotalElements(1));

        //list items ordered by id asc, receive last created
        get(ITEM_URL + "?" + "sort=id,asc" + "&size=1")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.THIRST.name())))
                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(1)));
    }
}
