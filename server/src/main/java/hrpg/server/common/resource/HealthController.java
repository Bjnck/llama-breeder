package hrpg.server.common.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping(path = "health-check")
    public String user() {
        return "OK";
    }
}
