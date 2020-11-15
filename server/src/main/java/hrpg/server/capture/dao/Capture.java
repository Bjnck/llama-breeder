package hrpg.server.capture.dao;

import hrpg.server.common.dao.WithUser;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "captures")
public class Capture extends WithUser {
    @Id
    private String id;

    @NotNull
    private Integer quality;

    private String creatureId;

    @NotNull
    private Instant startTime;
    @NotNull
    private Instant endTime;
}
