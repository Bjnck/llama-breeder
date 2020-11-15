package hrpg.server.common.resource.validation;

public interface ValidationService<T> {
    void validate(T toValidate);
}
