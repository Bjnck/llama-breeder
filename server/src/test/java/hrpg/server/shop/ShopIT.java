package hrpg.server.shop;

import hrpg.server.item.type.ItemCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShopIT {

    @Autowired
    private MockMvc mockMvc;

//    static OAuth2AuthenticationToken token = buildPrincipal();
    static MockHttpSession session = new MockHttpSession();

//    @BeforeAll
//    static void setUp(@Autowired UserService userService) {
//        userService.create(OAuthUserUtil.getRegistrationKey(
//                token.getAuthorizedClientRegistrationId(), token.getPrincipal()));
//
//        session.setAttribute(
//                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
//                new SecurityContextImpl(token));
//    }

//    private static OAuth2AuthenticationToken buildPrincipal() {
//        Map<String, Object> attributes = new HashMap<>();
//        attributes.put("sub", "my-id");
//
//        List<GrantedAuthority> authorities = Collections.singletonList(
//                new OAuth2UserAuthority("ROLE_USER", attributes));
//        OAuth2User user = new DefaultOAuth2User(authorities, attributes, "sub");
//
//        return new OAuth2AuthenticationToken(user, authorities, "testRegistrationId");
//    }

    @Test
    void listAllItems() throws Exception {
        mockMvc.perform(get("/shop/items")
                .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", equalTo(12)));
    }

    @Test
    void listItemsFilteredByCode() throws Exception {
        mockMvc.perform(get("/shop/items?code=" + ItemCode.NEST.name())
                .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].code", not(hasItem(ItemCode.LOVE.name()))))
                .andExpect(jsonPath("$.page.totalElements", equalTo(3)));
    }

    @Test
    void listItemsFilteredByQuality() throws Exception {
        mockMvc.perform(get("/shop/items?quality=" + 1)
                .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].quality", not(hasItem(2))))
                .andExpect(jsonPath("$.page.totalElements", equalTo(4)));
    }

    @Test
    void listItemsFilteredByCodeAndQuality() throws Exception {
        mockMvc.perform(get("/shop/items?" + "code=" + ItemCode.NEST.name() + "&quality=" + 1)
                .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.items[*].code", not(hasItem(ItemCode.LOVE.name()))))
                .andExpect(jsonPath("$._embedded.items[*].quality", not(hasItem(2))))
                .andExpect(jsonPath("$.page.totalElements", equalTo(1)));
    }
}
