package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "user")
@Getter
@Setter
public class UserProperties {
  private int startCoins;
}

