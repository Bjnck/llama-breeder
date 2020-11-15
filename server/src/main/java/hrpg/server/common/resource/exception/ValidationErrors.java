package hrpg.server.common.resource.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ValidationErrors {
    List<ValidationError> errors;

    @Override
    public String toString() {
        return '[' + errors.stream().map(ValidationError::toString).collect(Collectors.joining(",")) + ']';
    }
}
