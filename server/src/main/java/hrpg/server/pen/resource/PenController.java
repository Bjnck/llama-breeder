package hrpg.server.pen.resource;

import hrpg.server.common.resource.SortValues;
import hrpg.server.common.resource.exception.ResourceNotFoundException;
import hrpg.server.creature.resource.CreatureController;
import hrpg.server.item.resource.ItemController;
import hrpg.server.pen.service.PenService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(path = "pens")
@ExposesResourceFor(PenResponse.class)
public class PenController {

    public static final String COLLECTION_REF = "pens";

    private final TypedEntityLinks<PenResponse> links;
    private final PagedResourcesAssembler<PenResponse> pagedResourcesAssembler;

    private final PenService penService;
    private final PenResourceMapper penResourceMapper;

    public PenController(EntityLinks entityLinks,
                         PenService penService,
                         PenResourceMapper penResourceMapper,
                         PagedResourcesAssembler<PenResponse> pagedResourcesAssembler) {
        this.links = entityLinks.forType(PenResponse::getId);
        this.penService = penService;
        this.penResourceMapper = penResourceMapper;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping("{id}")
    public PenResponse get(@PathVariable long id) {
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
    public PagedModel<EntityModel<PenResponse>> search(
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PenResponse> responses = penService.search(pageable)
                .map(penResourceMapper::toResponse)
                .map(response -> {
                    addLinkToSetElt(response);
                    return response.add(links.linkToItemResource(response));
                });
        return pagedResourcesAssembler.toModel(responses);
    }

    private void addLinkToSetElt(PenResponse response) {
        response.getCreatures().forEach(creature ->
                creature.add(linkTo(CreatureController.class).slash(creature.getId()).withSelfRel()));
        response.getItems().forEach(item ->
                item.add(linkTo(ItemController.class).slash(item.getId()).withSelfRel()));
    }
}
