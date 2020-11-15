package hrpg.server.user.resource;

import hrpg.server.item.resource.ItemController;
import hrpg.server.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "user")
//@CrossOrigin(origins = {
//        "https://bjnck.stoplight.io",
//        "http://localhost:4200"
//})
//@CrossOrigin
public class UserController {

    private final UserService userService;
    private final UserResourceMapper userResourceMapper;

    public UserController(UserService userService,
                          UserResourceMapper userResourceMapper) {
        this.userService = userService;
        this.userResourceMapper = userResourceMapper;
    }

    @GetMapping
    public UserResponse user() {
        return userResourceMapper.toResponse(userService.get())
                .add(linkTo(UserController.class).withSelfRel(),
                        linkTo(ItemController.class).withRel(ItemController.ITEM_COLLECTION_VALUE));
    }
}
