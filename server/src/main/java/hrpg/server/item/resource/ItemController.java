package hrpg.server.item.resource;

import hrpg.server.common.exception.InsufficientCoinsException;
import hrpg.server.common.resource.SortValues;
import hrpg.server.common.resource.exception.ResourceNotFoundException;
import hrpg.server.common.resource.exception.ValidationCode;
import hrpg.server.common.resource.exception.ValidationError;
import hrpg.server.common.resource.exception.ValidationException;
import hrpg.server.item.service.ItemService;
import hrpg.server.item.service.exception.MaxItemsReachedException;
import hrpg.server.item.service.exception.ShopItemNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping(path = "items")
@ExposesResourceFor(ItemResponse.class)
public class ItemController {

    public static final String COLLECTION_REF = "items";

    private final TypedEntityLinks<ItemResponse> links;
    private final PagedResourcesAssembler<ItemResponse> pagedResourcesAssembler;

    private final ItemService itemService;
    private final ItemResourceMapper itemResourceMapper;

    public ItemController(EntityLinks entityLinks,
                          ItemService itemService,
                          ItemResourceMapper itemResourceMapper,
                          PagedResourcesAssembler<ItemResponse> pagedResourcesAssembler) {
        this.links = entityLinks.forType(ItemResponse::getId);
        this.itemService = itemService;
        this.itemResourceMapper = itemResourceMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ItemRequest request) {
        try {
            ItemResponse item = itemResourceMapper.toResponse(itemService.create(request.getCode(), request.getQuality()));
            return ResponseEntity.created(links.linkToItemResource(item).toUri()).build();
        } catch (ShopItemNotFoundException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("code,quality").code("shopItemNotFound").build()));
        } catch (InsufficientCoinsException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("code,quality").code(ValidationCode.INSUFFICIENT_COINS.getCode()).build()));
        } catch (MaxItemsReachedException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("_self").code(ValidationCode.MAX_SIZE.getCode()).build()));
        }
    }

    @GetMapping("{id}")
    public ItemResponse get(@PathVariable long id) {
        return itemService.findById(id)
                .map(itemResourceMapper::toResponse)
                .map(item -> item.add(links.linkToItemResource(item)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping
    @SortValues(values = {"id", "life"})
    public PagedModel<EntityModel<ItemResponse>> search(ItemQueryParams queryParams,
                                                        @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ItemResponse> items = itemService.search(itemResourceMapper.toSearch(queryParams), pageable)
                .map(itemResourceMapper::toResponse)
                .map(item -> item.add(links.linkToItemResource(item)));
        return pagedResourcesAssembler.toModel(items);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        itemService.delete(id);
    }
}
