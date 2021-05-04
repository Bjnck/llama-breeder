package hrpg.server.item;

import hrpg.server.AbstractIntegrationTest;
import hrpg.server.item.resource.ItemRequest;
import hrpg.server.item.type.ItemCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ItemEndpointIT extends AbstractIntegrationTest {

    private static final String ITEM_URL = "/items";
    @Test
    void testItemEndpoints() throws Exception {
        //get all items found 0
        get(ITEM_URL)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(0));

        //create item
        String location = post(ITEM_URL, ItemRequest.builder().code(ItemCode.NEST).quality(2).build())
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());

        //get item
        get(location)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(location.substring(location.lastIndexOf("/")+1))))
                .andExpect(jsonPath("$.code", equalTo(ItemCode.NEST.name())))
                .andExpect(jsonPath("$.quality", equalTo(2)))
                .andExpect(jsonPath("$.life", equalTo(100)))
                .andExpect(jsonPath("$._links.self.href", equalTo(location)));

        //get all items found 1
        get(ITEM_URL)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(1))
                .andExpect(jsonPath("$._embedded.items[*].id", hasItem(location.substring(location.lastIndexOf("/")+1))))
                .andExpect(jsonPath("$._embedded.items[*].code", hasItem(ItemCode.NEST.name())))
                .andExpect(jsonPath("$._embedded.items[*].quality", hasItem(2)))
                .andExpect(jsonPath("$._embedded.items[*].life", hasItem(100)))
                .andExpect(jsonPath("$._embedded.items[*]._links.self.href", hasItem(location)));

        //delete item
        delete(location)
                .andDo(print())
                .andExpect(status().isNoContent());
        get(location)
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
