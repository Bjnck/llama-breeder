package hrpg.server.item.resource.validation;

import hrpg.server.common.resource.validation.AbstractValidationService;
import hrpg.server.common.resource.validation.ValidationContextService;
import hrpg.server.common.resource.validation.ValidationRule;
import hrpg.server.item.resource.ItemRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemValidationService extends AbstractValidationService<ItemRequest, ItemValidationContext> {
    public ItemValidationService(ValidationContextService<ItemRequest, ItemValidationContext> validationContextService,
                                 List<ValidationRule<ItemRequest, ItemValidationContext>> rules) {
        super(validationContextService, rules);
    }
}
