package hrpg.server.capture.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CaptureRequest {
    @Min(0)
    @Max(3)
    @Builder.Default
    private int quality = 0;
    @Min(1)
    @Max(8)
    private Integer bait;
}
