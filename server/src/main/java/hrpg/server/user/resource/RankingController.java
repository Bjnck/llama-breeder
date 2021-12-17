package hrpg.server.user.resource;

import hrpg.server.item.resource.ItemController;
import hrpg.server.shop.resource.ShopItemController;
import hrpg.server.user.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "users")
public class RankingController {

    private final UserService userService;
    private final UserResourceMapper userResourceMapper;

    public RankingController(UserService userService,
                             UserResourceMapper userResourceMapper) {
        this.userService = userService;
        this.userResourceMapper = userResourceMapper;
    }

    @GetMapping
    public RankingResponse ranking() {
        return userResourceMapper.toResponse(userService.get())
                .add(linkTo(RankingController.class).withSelfRel(),
                        linkTo(ItemController.class).withRel(ItemController.COLLECTION_REF),
                        linkTo(ShopItemController.class).withRel(ShopItemController.COLLECTION_REF));
    }
}
