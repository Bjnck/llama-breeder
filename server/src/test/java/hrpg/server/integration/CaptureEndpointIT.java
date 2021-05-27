package hrpg.server.integration;

import hrpg.server.capture.resource.CaptureRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CaptureEndpointIT extends AbstractIntegrationTest {

    @Test
    @SneakyThrows
    void capture_endpoints() {
        //create new capture
        String location = post(CAPTURE_URL, CaptureRequest.builder().build())
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());

        //get capture
        get(location)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathIdFromLocation(location))
                .andExpect(jsonPath("$.startTime", notNullValue()))
                .andExpect(jsonPath("$.endTime", notNullValue()))
                .andExpect(jsonPath("$.creatureId", nullValue()))
                .andExpect(jsonPath("$.quality", equalTo(0)))
                .andExpect(jsonPath("$._links.self.href", equalTo(location)));
    }

//    @Test
//    void createMultipleAndSearch() throws Exception {
//        String capture1Location = post("/captures", CaptureRequest.builder().build())
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        CaptureResponse capture1 = objectMapper.readValue(get(capture1Location)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString(), CaptureResponse.class);
//        Thread.sleep(1000);
//        String capture2Location = post("/captures", CaptureRequest.builder().build())
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        CaptureResponse capture2 = objectMapper.readValue(get(capture2Location)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString(), CaptureResponse.class);
//
//        get("/captures")
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$._embedded.captures[*].id", hasItems(capture1.getId(), capture2.getId())))
//                .andExpect(jsonPath("$.page.totalElements", equalTo(2)));
//    }
}
