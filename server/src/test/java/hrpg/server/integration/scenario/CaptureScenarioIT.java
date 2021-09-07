package hrpg.server.integration.scenario;

import hrpg.server.capture.dao.Bait;
import hrpg.server.capture.dao.BaitRepository;
import hrpg.server.capture.resource.CaptureRequest;
import hrpg.server.capture.resource.CaptureResponse;
import hrpg.server.integration.AbstractIntegrationTest;
import hrpg.server.item.resource.ItemRequest;
import hrpg.server.item.type.ItemCode;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("capture_scenario")
class CaptureScenarioIT extends AbstractIntegrationTest {

    @Autowired
    private BaitRepository baitRepository;

    @Test
    @SneakyThrows
    void captureCreature_scenario() {
        //create first capture
        String location = post(CAPTURE_URL, CaptureRequest.builder().build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());

        //wait for first capture
        CaptureResponse capture = objectMapper.readValue(get(location)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture.getEndTime(), ChronoUnit.MILLIS));

        //get capture
        get(location)
                .andExpect(status().isOk())
                .andExpect(jsonPathIdFromLocation(location))
                .andExpect(jsonPath("$.startTime", notNullValue()))
                .andExpect(jsonPath("$.endTime", notNullValue()))
                .andExpect(jsonPath("$.creatureId", notNullValue()))
                .andExpect(jsonPath("$.._links.creature.href", notNullValue()))
                .andExpect(jsonPath("$.quality", equalTo(0)))
                .andExpect(jsonPath("$._links.self.href", equalTo(location)));
    }

    @Test
    @SneakyThrows
    void runningAndLimit_scenario() {
        //create first capture
        String location1 = post(CAPTURE_URL, CaptureRequest.builder().build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location1, notNullValue());

        //create second capture fail
        post(CAPTURE_URL, CaptureRequest.builder().build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", hasItem("_self")))
                .andExpect(jsonPath("$[*].code", hasItem("runningCapture")));

        //wait for first capture
        CaptureResponse capture1 = objectMapper.readValue(get(location1)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture1.getEndTime(), ChronoUnit.MILLIS));

        //create second capture and wait
        String location2 = post(CAPTURE_URL, CaptureRequest.builder().build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location2, notNullValue());
        CaptureResponse capture2 = objectMapper.readValue(get(location2)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture2.getEndTime(), ChronoUnit.MILLIS));

        //create third capture, limit capture exceeded (2), first capture should be deleted
        post(CAPTURE_URL, CaptureRequest.builder().build()).andExpect(status().isCreated());
        get(location1).andExpect(status().isNotFound());

        //todo second capture should still be there
    }

    @Test
    @SneakyThrows
    void net_scenario() {
        //shop net
        post(ITEM_URL, ItemRequest.builder().code(ItemCode.NET).quality(1).build()).andExpect(status().isCreated());

        //create and wait for capture with available net
        String location = post(CAPTURE_URL, CaptureRequest.builder().quality(1).build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        CaptureResponse capture = objectMapper.readValue(get(location)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture.getEndTime(), ChronoUnit.MILLIS));

        //create capture with unavailable net
        post(CAPTURE_URL, CaptureRequest.builder().quality(1).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", hasItem("quality")))
                .andExpect(jsonPath("$[*].code", hasItem("unavailable")));
    }

    @Test
    @SneakyThrows
    void bait_scenario() {
        //create and wait for capture without net but with bait, bait should be ignore
        String location1 = post(CAPTURE_URL, CaptureRequest.builder().bait(1).build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location1, notNullValue());
        CaptureResponse capture1 = objectMapper.readValue(get(location1)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture1.getEndTime(), ChronoUnit.MILLIS));

        //shop 2 nets
        post(ITEM_URL, ItemRequest.builder().code(ItemCode.NET).quality(1).build()).andExpect(status().isCreated());
        post(ITEM_URL, ItemRequest.builder().code(ItemCode.NET).quality(1).build()).andExpect(status().isCreated());
        //add bait
        addBait();

        //create and wait for capture with available net and bait
        String location2 = post(CAPTURE_URL, CaptureRequest.builder().quality(1).bait(1).build())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        CaptureResponse capture2 = objectMapper.readValue(get(location2)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsByteArray(), CaptureResponse.class);
        Thread.sleep(LocalDateTime.now().until(capture2.getEndTime(), ChronoUnit.MILLIS));

        //create capture with available net and unavailable bait
        post(CAPTURE_URL, CaptureRequest.builder().quality(1).bait(1).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[*].field", hasItem("bait")))
                .andExpect(jsonPath("$[*].code", hasItem("unavailable")));
    }

    private void addBait() {
        Bait bait = Bait.builder().generation(1).count(1).build();
        bait.setUserId(userDto.getId());
        baitRepository.save(bait);
    }
}
