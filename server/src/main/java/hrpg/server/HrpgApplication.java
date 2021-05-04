package hrpg.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableHypermediaSupport(type = {HypermediaType.HAL})
@EnableSpringDataWebSupport
public class HrpgApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrpgApplication.class, args);
    }
}
