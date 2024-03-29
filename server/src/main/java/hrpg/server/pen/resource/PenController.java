package hrpg.server.pen.resource;

import hrpg.server.common.resource.SortValues;
import hrpg.server.common.resource.exception.ResourceNotFoundException;
import hrpg.server.common.resource.exception.ValidationCode;
import hrpg.server.common.resource.exception.ValidationError;
import hrpg.server.common.resource.exception.ValidationException;
import hrpg.server.creature.resource.CreatureController;
import hrpg.server.creature.service.exception.CreatureInUseException;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.item.resource.ItemController;
import hrpg.server.item.service.exception.ItemInUseException;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.service.exception.MaxItemsException;
import hrpg.server.pen.service.PenComputor;
import hrpg.server.pen.service.PenInfoService;
import hrpg.server.pen.service.PenService;
import hrpg.server.pen.service.exception.InvalidPenSizeException;
import hrpg.server.pen.service.exception.PenNotFoundException;
import hrpg.server.pen.service.exception.TooManyPenException;
import hrpg.server.user.service.exception.InsufficientCoinsException;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "pens")
@ExposesResourceFor(PenResponse.class)
public class PenController {
    private final TypedEntityLinks<PenResponse> links;
    private final PagedResourcesAssembler<PenResponse> pagedResourcesAssembler;

    private final PenService penService;
    private final PenInfoService penInfoService;
    private final PenResourceMapper penResourceMapper;
    private final PenComputor penComputor;

    public PenController(EntityLinks entityLinks,
                         PenService penService,
                         PenInfoService penInfoService, PenResourceMapper penResourceMapper,
                         PagedResourcesAssembler<PenResponse> pagedResourcesAssembler,
                         PenComputor penComputor) {
        this.links = entityLinks.forType(PenResponse::getId);
        this.penService = penService;
        this.penInfoService = penInfoService;
        this.penResourceMapper = penResourceMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.penComputor = penComputor;
    }

    @PostMapping()
    public ResponseEntity<Object> create() {
        try {
            PenResponse response = penResourceMapper.toResponse(penService.create());
            return ResponseEntity.created(links.linkToItemResource(response).toUri())
                    .header("Access-Control-Expose-Headers", HttpHeaders.LOCATION).build();
        } catch (TooManyPenException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("_self").code(ValidationCode.MAX_SIZE.getCode()).build()));
        } catch (InsufficientCoinsException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("_self").code(ValidationCode.INSUFFICIENT_COINS.getCode()).build()));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable long id, @Valid @RequestBody PenRequest request) {
        //need to compute first if a creature or item is removed
        penComputor.compute(id);
        try {
            PenResponse response = penResourceMapper.toResponse(penService.update(id, penResourceMapper.toDto(request)));
            return ResponseEntity.ok().header(HttpHeaders.LOCATION, links.linkToItemResource(response).toUri().toString()).build();
        } catch (PenNotFoundException e) {
            throw new ResourceNotFoundException();
        } catch (CreatureNotFoundException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("creatures.id").code(ValidationCode.INVALID_VALUE.getCode())
                            .value(Optional.ofNullable(e.getId()).map(Object::toString).orElse(null)).build()));
        } catch (MaxCreaturesException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("creatures").code(ValidationCode.MAX_SIZE.getCode()).build()));
        } catch (CreatureInUseException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("creatures.id").code(ValidationCode.CONFLICT.getCode())
                            .value(Optional.ofNullable(e.getId()).map(Object::toString).orElse(null)).build()));
        } catch (ItemNotFoundException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("items.id").code(ValidationCode.INVALID_VALUE.getCode())
                            .value(Optional.ofNullable(e.getId()).map(Object::toString).orElse(null)).build()));
        } catch (MaxItemsException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("items").code(ValidationCode.MAX_SIZE.getCode()).build()));
        } catch (ItemInUseException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("items.id").code(ValidationCode.CONFLICT.getCode())
                            .value(Optional.ofNullable(e.getId()).map(Object::toString).orElse(null)).build()));
        } catch (InsufficientCoinsException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("size").code(ValidationCode.INSUFFICIENT_COINS.getCode()).build()));
        } catch (InvalidPenSizeException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("size").code(ValidationCode.INVALID_VALUE.getCode()).build()));
        }
    }

    @GetMapping("{id}")
    public PenResponse get(@PathVariable long id) {
        penComputor.compute(id);
        return penService.findById(id)
                .map(penResourceMapper::toResponse)
                .map(response -> {
                    addLinkToSetElt(response);
                    return response.add(links.linkToItemResource(response));
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping
    @SortValues(values = {"id"})
    public PagedModel<EntityModel<PenResponse>> search(@RequestParam(defaultValue = "true") boolean compute,
                                                       @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if (compute) penComputor.compute();

        Page<PenResponse> responses = penService.search(pageable)
                .map(penResourceMapper::toResponse)
                .map(response -> {
                    addLinkToSetElt(response);
                    return response.add(links.linkToItemResource(response));
                });
        return pagedResourcesAssembler.toModel(responses);
    }

    @PostMapping("{id}/action/activate-item/{itemId}")
    public PenActivationResponse activateItem(@PathVariable long id, @PathVariable long itemId) {
        try {
            return penResourceMapper.toResponse(penService.activateItem(id, itemId));
        } catch (ItemNotFoundException | PenNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    private void addLinkToSetElt(PenResponse response) {
        response.getCreatures().forEach(creature ->
                creature.add(linkTo(CreatureController.class).slash(creature.getId()).withSelfRel()));
        response.getItems().forEach(item ->
                item.add(linkTo(ItemController.class).slash(item.getId()).withSelfRel()));
    }

    @GetMapping("info")
    public List<PenInfo> info() {
        return penInfoService.getInfo();
    }
}
