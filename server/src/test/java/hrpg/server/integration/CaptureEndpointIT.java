package hrpg.server.integration;

import hrpg.server.capture.resource.CaptureRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CaptureEndpointIT extends AbstractIntegrationTest {

    @Test
    @SneakyThrows
    void capture_endpoints() {
        //create new capture
        String location = post(CAPTURE_URL, CaptureRequest.builder().build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());

        //get capture in progress
        get(location)
                .andExpect(status().isOk())
                .andExpect(jsonPathIdFromLocation(location))
                .andExpect(jsonPath("$.startTime", notNullValue()))
                .andExpect(jsonPath("$.endTime", notNullValue()))
                .andExpect(jsonPath("$.creatureId", nullValue()))
                .andExpect(jsonPath("$.quality", equalTo(0)))
                .andExpect(jsonPath("$._links.self.href", equalTo(location)));

        //get all captures
        get(CAPTURE_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements", equalTo(1)));
    }
}
