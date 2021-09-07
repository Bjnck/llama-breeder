package hrpg.server.capture.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CaptureDto {
    private Long id;

    private Long creatureId;

    private int quality;
    private Integer baitGeneration;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
