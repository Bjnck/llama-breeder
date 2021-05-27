package hrpg.server.integration.scenario;

import hrpg.server.integration.AbstractIntegrationTest;
import hrpg.server.capture.resource.CaptureRequest;
import hrpg.server.capture.resource.CaptureResponse;
import hrpg.server.creature.resource.CreatureResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TutorialScenarioIT extends AbstractIntegrationTest {

    @Test
    @SneakyThrows
    void tutorial_scenario() {
        //get newly created user, check level is 0
        get(USER_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level", equalTo(0)));

        //first capture and get creature
        String locationCapture1 = post(CAPTURE_URL, CaptureRequest.builder().build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(locationCapture1, notNullValue());
        //wait for capture
        CaptureResponse capture1 = objectMapper.readValue(get(locationCapture1)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture1.getEndTime(), ChronoUnit.MILLIS));
        //get creature
        capture1 = objectMapper.readValue(get(locationCapture1)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        CreatureResponse creature1 = objectMapper.readValue(get(CREATURE_URL + "/" + capture1.getCreatureId())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CreatureResponse.class);

        //second capture and get creature
        String locationCapture2 = post(CAPTURE_URL, CaptureRequest.builder().build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(locationCapture2, notNullValue());
        //wait for capture
        CaptureResponse capture2 = objectMapper.readValue(get(locationCapture2)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture2.getEndTime(), ChronoUnit.MILLIS));
        //get creature
        capture2 = objectMapper.readValue(get(locationCapture2)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        CreatureResponse creature2 = objectMapper.readValue(get(CREATURE_URL + "/" + capture2.getCreatureId())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CreatureResponse.class);

        //compare creatures
        assertThat(creature1.getSex(), not(equalTo(creature2.getSex())));
        assertThat(creature1.getColors().getColor1().getCode(), not(equalTo(creature2.getColors().getColor1().getCode())));

        //check level is 1
        get(USER_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.level", equalTo(1)));

    }
}
