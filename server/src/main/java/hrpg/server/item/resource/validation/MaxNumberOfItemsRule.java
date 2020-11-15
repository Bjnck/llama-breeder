package hrpg.server.item.resource.validation;

import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.resource.exception.ValidationError;
import hrpg.server.common.resource.validation.ValidationRule;
import hrpg.server.item.resource.ItemRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class MaxNumberOfItemsRule implements ValidationRule<ItemRequest, ItemValidationContext> {

    private final ParametersProperties parametersProperties;

    public MaxNumberOfItemsRule(ParametersProperties parametersProperties) {
        this.parametersProperties = parametersProperties;
    }

    @Override
    public Collection<ValidationError> validate(ItemRequest toValidate, ItemValidationContext context) {
        Collection<ValidationError> errors = new HashSet<>();
        if (context.getNumberOfItems() >= parametersProperties.getItems().getMax())
            errors.add(ValidationError.builder().field("_self").code("tooMany").build());
        return errors;
    }
}
