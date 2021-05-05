package hrpg.server.creature.resource;

import hrpg.server.common.resource.SortValues;
import hrpg.server.common.resource.exception.ResourceNotFoundException;
import hrpg.server.creature.service.CreatureService;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "creatures")
@ExposesResourceFor(CreatureResponse.class)
public class CreatureController {

    public static final String COLLECTION_REF = "creatures";

    private final TypedEntityLinks<CreatureResponse> links;
    private final PagedResourcesAssembler<CreatureResponse> pagedResourcesAssembler;

    private final CreatureService creatureService;
    private final CreatureResourceMapper creatureResourceMapper;

    public CreatureController(EntityLinks entityLinks,
                              PagedResourcesAssembler<CreatureResponse> pagedResourcesAssembler,
                              CreatureService creatureService,
                              CreatureResourceMapper creatureResourceMapper) {
        this.links = entityLinks.forType(CreatureResponse::getId);
        this.pagedResourcesAssembler = pagedResourcesAssembler;

        this.creatureService = creatureService;
        this.creatureResourceMapper = creatureResourceMapper;
    }

    @GetMapping("{id}")
    public CreatureResponse get(@PathVariable long id) {
        return creatureService.findById(id)
                .map(creatureResourceMapper::toResponse)
                .map(response -> response.add(links.linkToItemResource(response)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping
    @SortValues(values = {"id"})
    public PagedModel<EntityModel<CreatureResponse>> search(CreatureQueryParams queryParams,
                                                            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CreatureResponse> responses = creatureService.search(creatureResourceMapper.toSearch(queryParams), pageable)
                .map(creatureResourceMapper::toResponse)
                .map(response -> response.add(links.linkToItemResource(response)));
        return pagedResourcesAssembler.toModel(responses);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id) {
        //todo should return coins
        try {
            creatureService.delete(id);
        } catch (CreatureNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
}
