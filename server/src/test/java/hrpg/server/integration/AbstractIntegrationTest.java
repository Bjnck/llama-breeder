package hrpg.server.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.security.CustomPrincipal;
import hrpg.server.user.service.UserDto;
import hrpg.server.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

    protected static final String USER_URL = "/user";
    protected static final String CAPTURE_URL = "/captures";
    protected static final String CREATURE_URL = "/creatures";
    protected static final String ITEM_URL = "/items";
    protected static final String PEN_URL = "/pens";
    protected static final String SHOP_URL = "/shop-items";

    private static final String AUTHORIZED_CLIENT_REGISTRATION_ID = "testRegistrationId";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ParametersProperties parametersProperties;

    BearerTokenAuthentication authentication;
    protected UserDto userDto;
    MockHttpSession session = new MockHttpSession();
    protected ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(@Autowired UserService userService) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        String userSub = UUID.randomUUID().toString();
        userDto = userService.create(AUTHORIZED_CLIENT_REGISTRATION_ID + "." + userSub);
        authentication = buildPrincipal(userSub, userDto.getId());
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new SecurityContextImpl(authentication));

        //todo create a second user with data in all tables
    }

    private BearerTokenAuthentication buildPrincipal(String userSub, Integer userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("registration", AUTHORIZED_CLIENT_REGISTRATION_ID);
        attributes.put("registration_id", "sub");
        attributes.put("sub", userSub);
        List<GrantedAuthority> authorities = Collections.singletonList(
                new OAuth2UserAuthority("ROLE_USER", attributes));
        CustomPrincipal user = new CustomPrincipal(attributes, authorities);
        user.setUserId(userId);

        return new BearerTokenAuthentication(user,
                new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(), Instant.now().plus(1, ChronoUnit.DAYS)),
                authorities);
    }

    protected ResultActions post(String urlTemplate, Object body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                .session(session)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)));
    }

    protected ResultActions put(String urlTemplate, Object body) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.put(urlTemplate)
                .session(session)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body)));
    }

    protected ResultActions get(String urlTemplate) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get(urlTemplate)
                .session(session));
    }

    protected ResultActions delete(String urlTemplate) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.delete(urlTemplate)
                .session(session)
                .with(csrf()));
    }

    protected static ResultMatcher jsonPathTotalElements(int totalElements) {
        return jsonPath("$.page.totalElements", equalTo(totalElements));
    }

    protected static ResultMatcher jsonPathIdFromLocation(@NotNull String location) {
        return jsonPath("$.id", equalTo(Integer.valueOf(location.substring(location.lastIndexOf("/") + 1))));
    }
}
