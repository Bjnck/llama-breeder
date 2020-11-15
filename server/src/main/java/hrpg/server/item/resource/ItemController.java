package hrpg.server.item.resource;

import hrpg.server.common.resource.exception.ResourceNotFoundException;
import hrpg.server.item.resource.validation.ItemValidationService;
import hrpg.server.item.service.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.TypedEntityLinks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "items")
@ExposesResourceFor(ItemResponse.class)
public class ItemController {

    public static final String ITEM_COLLECTION_VALUE = "items";

    private final TypedEntityLinks<ItemResponse> links;
    private final PagedResourcesAssembler<ItemResponse> pagedResourcesAssembler;

    private final ItemService itemService;
    private final ItemResourceMapper itemResourceMapper;
    private final ItemValidationService itemValidationService;

    public ItemController(EntityLinks entityLinks,
                          ItemService itemService,
                          ItemResourceMapper itemResourceMapper,
                          PagedResourcesAssembler<ItemResponse> pagedResourcesAssembler,
                          ItemValidationService itemValidationService) {
        this.links = entityLinks.forType(ItemResponse::getId);
        this.itemService = itemService;
        this.itemResourceMapper = itemResourceMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.itemValidationService = itemValidationService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody ItemRequest request) {
        itemValidationService.validate(request);
        ItemResponse item = itemResourceMapper.toResponse(itemService.create(itemResourceMapper.toRequest(request)));
        return ResponseEntity.created(links.linkToItemResource(item).toUri()).build();
    }

    @GetMapping("{id}")
    public ItemResponse get(@PathVariable String id) {
        return itemService.findById(id)
                .map(itemResourceMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("id"));
    }

    @GetMapping
    public PagedModel<EntityModel<ItemResponse>> search(Pageable pageable) {
        Page<ItemResponse> items = itemService.search(null, pageable)
                .map(itemResourceMapper::toResponse)
                .map(item -> item.add(links.linkToItemResource(item)));
        return pagedResourcesAssembler.toModel(items);
    }
}
