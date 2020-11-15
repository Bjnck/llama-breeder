package hrpg.server.item.resource.validation;

import hrpg.server.common.resource.exception.ValidationError;
import hrpg.server.common.resource.validation.ValidationRule;
import hrpg.server.item.resource.ItemRequest;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class ShopItemExistsRule implements ValidationRule<ItemRequest, ItemValidationContext> {
    @Override
    public Collection<ValidationError> validate(ItemRequest toValidate, ItemValidationContext context) {
        Collection<ValidationError> errors = new HashSet<>();
        if(context.getShopItem() == null)
            errors.add(ValidationError.builder().field("code,quality").code("notFound").build());
        return errors;
    }
}
