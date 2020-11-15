package hrpg.server.user;

import hrpg.server.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserIT extends AbstractIntegrationTest {
    @Test
    void getNewUser() throws Exception {
        get("/user")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", notNullValue()))
                .andExpect(jsonPath("$.level", equalTo(0)))
                .andExpect(jsonPath("$.coins", equalTo(parametersProperties.getUser().getStartCoins())))
                .andExpect(jsonPath("$._links.self.href", endsWith("/user")))
                .andExpect(jsonPath("$._links.items.href", endsWith("/items")));
    }
}
