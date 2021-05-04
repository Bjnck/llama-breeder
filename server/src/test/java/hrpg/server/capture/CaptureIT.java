//package hrpg.server.capture;
//
//import hrpg.server.AbstractIntegrationTest;
//import hrpg.server.capture.dao.CaptureRepository;
//import hrpg.server.capture.resource.CaptureRequest;
//import hrpg.server.capture.resource.CaptureResponse;
//import hrpg.server.common.security.CustomPrincipal;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class CaptureIT extends AbstractIntegrationTest {
//
//    @Autowired
//    private CaptureRepository captureRepository;
//
//    @AfterEach
//    public void afterEach() {
//        captureRepository.deleteByUserId(((CustomPrincipal) authentication.getPrincipal()).getUserId());
//    }
//
//    @Test
//    void captureSuccessAndSecondCaptureFail() throws Exception {
//        post("/captures", CaptureRequest.builder().build())
//                .andDo(print())
//                .andExpect(status().isCreated());
//
//        post("/captures", CaptureRequest.builder().build())
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void captureWithNestUnavailable() throws Exception {
//        post("/captures", CaptureRequest.builder().quality(1).build())
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
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
//}
