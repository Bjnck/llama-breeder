package hrpg.server.common.resource.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {
    private String field;
    private String code;
    private String value;
}
