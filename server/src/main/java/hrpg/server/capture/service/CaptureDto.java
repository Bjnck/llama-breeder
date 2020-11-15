package hrpg.server.capture.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CaptureDto {
    private String id;

    private int quality;

    private String creatureId;

    private Instant startTime;
    private Instant endTime;
}
