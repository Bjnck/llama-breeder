package hrpg.server.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "captures")
@Getter
@Setter
public class CapturesProperties {
  private int time;
}

