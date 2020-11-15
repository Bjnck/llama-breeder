package hrpg.server;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.security.CustomPrincipal;
import hrpg.server.user.service.UserDto;
import hrpg.server.user.service.UserService;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration")
public abstract class AbstractIntegrationTest {

    private final static String AUTHORIZED_CLIENT_REGISTRATION_ID = "testRegistrationId";
    private final static String USER_SUB = UUID.randomUUID().toString();

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ParametersProperties parametersProperties;

    protected static BearerTokenAuthentication authentication;
    static MockHttpSession session = new MockHttpSession();
    protected static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp(@Autowired UserService userService) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        UserDto userDto = userService.create(AUTHORIZED_CLIENT_REGISTRATION_ID + "." + USER_SUB);
        authentication = buildPrincipal(userDto.getId());
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new SecurityContextImpl(authentication));
    }

    private static BearerTokenAuthentication buildPrincipal(String userId) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("registration", AUTHORIZED_CLIENT_REGISTRATION_ID);
        attributes.put("registration_id", "sub");
        attributes.put("sub", USER_SUB);
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

    protected static ResultMatcher jsonPathTotalElements(int totalElements) {
        return jsonPath("$.page.totalElements", equalTo(totalElements));
    }
}
