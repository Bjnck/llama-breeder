package hrpg.server;

import com.github.cloudyrock.spring.v5.EnableMongock;
import hrpg.server.common.properties.CapturesProperties;
import hrpg.server.common.properties.ItemsProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableHypermediaSupport(type = {HypermediaType.HAL})
@EnableSpringDataWebSupport
@EnableMongock
@EnableConfigurationProperties({
        ParametersProperties.class,
        UserProperties.class,
        ItemsProperties.class,
        CapturesProperties.class
})
public class HrpgApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrpgApplication.class, args);
    }
}
