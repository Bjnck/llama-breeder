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
                        linkTo(ItemController.class).withRel(ItemController.COLLECTION_REF),
                        linkTo(ShopItemController.class).withRel(ShopItemController.COLLECTION_REF));
    }

    @PutMapping
    public ResponseEntity<Object> update(@Valid @RequestBody UserRequest request) {
        userService.updateName(request.getName());
        return ResponseEntity.ok().header(HttpHeaders.LOCATION, linkTo(UserController.class).toUri().toString()).build();
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete() {
        userService.delete();
    }
}
