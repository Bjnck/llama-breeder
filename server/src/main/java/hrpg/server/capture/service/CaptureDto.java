package hrpg.server.capture.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CaptureDto {
    private String id;

    private String creatureId;

    private int quality;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
