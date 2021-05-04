package hrpg.server.user;

import hrpg.server.AbstractIntegrationTest;
import hrpg.server.user.resource.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserEndpointIT extends AbstractIntegrationTest {

    private static final String USER_URL = "/user";

    @Test
    void testUserEndpoints() throws Exception {
        //get new user (previously created in AbstractIntegrationTest.setUp())
        MvcResult userResult = get(USER_URL)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", nullValue()))
                .andExpect(jsonPath("$.level", equalTo(0)))
                .andExpect(jsonPath("$.coins", equalTo(parametersProperties.getUser().getStartCoins())))
                .andExpect(jsonPath("$._links.self.href", endsWith("/user")))
                .andExpect(jsonPath("$._links.items.href", endsWith("/items")))
                .andExpect(jsonPath("$._links.shop.href", endsWith("/shop-items")))
                .andReturn();
        UserResponse userResponse = objectMapper.readValue(userResult.getResponse().getContentAsByteArray(), UserResponse.class);

        //update user name
        String name = UUID.randomUUID().toString();
        String location = put(USER_URL, userResponse.toBuilder().name(name).build())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());
        get(location)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(name)));

        //delete user
        delete(USER_URL)
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
