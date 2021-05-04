package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "parameters")
@Getter
@Setter
public class ParametersProperties {
    private ItemsProperties items;
    private CapturesProperties captures;
    private UserProperties user;
}

