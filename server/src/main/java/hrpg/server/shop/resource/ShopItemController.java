package hrpg.server.shop.resource;

import hrpg.server.shop.service.ShopItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "shop/items")
@ExposesResourceFor(ShopItemResponse.class)
public class ShopItemController {

    public static final String SHOP_COLLECTION_VALUE = "shop";

    private final ShopItemService shopItemService;
    private final ShopItemResourceMapper shopItemResourceMapper;
    private final PagedResourcesAssembler<ShopItemResponse> pagedResourcesAssembler;

    public ShopItemController(ShopItemService shopItemService,
                              ShopItemResourceMapper shopItemResourceMapper,
                              PagedResourcesAssembler<ShopItemResponse> pagedResourcesAssembler) {
        this.shopItemService = shopItemService;
        this.shopItemResourceMapper = shopItemResourceMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping
    public PagedModel<EntityModel<ShopItemResponse>> search(ShopItemSearchParams searchParams, Pageable pageable) {
        Page<ShopItemResponse> items = shopItemService.search(shopItemResourceMapper.toSearch(searchParams), pageable)
                .map(shopItemResourceMapper::toResponse);
        return pagedResourcesAssembler.toModel(items);
    }
}
