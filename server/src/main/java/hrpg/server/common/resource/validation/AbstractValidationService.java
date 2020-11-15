package hrpg.server.common.resource.validation;

import hrpg.server.common.resource.exception.ValidationError;
import hrpg.server.common.resource.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class AbstractValidationService<T, C> implements ValidationService<T> {
    private final ValidationContextService<T, C> validationContextService;
    private final List<ValidationRule<T, C>> rules;

    public AbstractValidationService(ValidationContextService<T, C> validationContextService,
                                     List<ValidationRule<T, C>> rules) {
        this.validationContextService = validationContextService;
        this.rules = rules;
    }

    @Override
    public void validate(T toValidate) {
        C context = validationContextService.getContext(toValidate);
        List<ValidationError> errors = new ArrayList<>();
        rules.forEach(rule -> errors.addAll(rule.validate(toValidate, context)));
        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
