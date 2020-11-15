package hrpg.server.common.resource.validation;

import hrpg.server.common.resource.exception.ValidationError;

import java.util.Collection;

public interface ValidationRule<T, C> {
    Collection<ValidationError> validate(T toValidate, C context);
}
