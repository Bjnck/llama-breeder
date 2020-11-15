package hrpg.server.item;

import hrpg.server.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ItemIT extends AbstractIntegrationTest {
    @Test
    void buyItem() throws Exception {
        get("/items")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(0));

        String location =
                post("/items", null)
//                post("/items", ItemRequest.builder().code(ItemCode.NEST).quality(1).build())
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);

        get(location)
                .andDo(print())
                .andExpect(status().isOk());

        get("/items")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(1));
    }
}
