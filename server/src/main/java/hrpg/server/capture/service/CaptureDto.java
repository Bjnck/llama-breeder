package hrpg.server.capture.service;

import hrpg.server.creature.service.CreatureInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CaptureDto {
    private Long id;

    private CreatureInfoDto creatureInfo;

    private int quality;
    private Integer baitGeneration;

    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
}
