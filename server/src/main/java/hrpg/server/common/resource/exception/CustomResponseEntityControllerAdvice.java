package hrpg.server.common.resource.exception;

import hrpg.server.common.exception.ConflictException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
class CustomResponseEntityControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public void handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException.class)
    public void handleConflictException(ConflictException e) {
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public void handleResourceNotFoundException(ResourceNotFoundException e) {
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(e.getErrors()
                .stream()
                .map(error -> ErrorResponse.builder()
                        .field(error.getField())
                        .code(error.getCode())
                        .value(error.getValue())
                        .build())
                .collect(Collectors.toList()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> ErrorResponse.builder()
                        .field(error.getField())
                        .code(getCode(error.getCode()))
                        .build())
                .collect(Collectors.toList());
        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    private String getCode(String code) {
        if (code == null)
            return null;
        if (code.equals("NotBlank"))
            return "required";
        return code;
    }
}
