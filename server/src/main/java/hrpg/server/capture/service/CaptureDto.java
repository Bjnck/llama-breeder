package hrpg.server.capture.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CaptureDto {
    private Long id;

    private Long creatureId;

    private int quality;
    private Integer baitGeneration;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
