package hrpg.server.user.resource;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    private String name;
    private long points;
}
