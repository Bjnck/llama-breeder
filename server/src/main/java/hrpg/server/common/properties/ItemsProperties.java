package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "items")
@Getter
@Setter
public class ItemsProperties {
  private int max;
}

