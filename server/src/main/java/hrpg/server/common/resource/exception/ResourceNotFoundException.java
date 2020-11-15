package hrpg.server.common.resource.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private String field;

    public ResourceNotFoundException(String field) {
        this.field = field;
    }
}
