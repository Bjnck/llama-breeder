package hrpg.server.creature.resource;

import hrpg.server.common.resource.SortValues;
import hrpg.server.common.resource.exception.ResourceNotFoundException;
import hrpg.server.common.resource.exception.ValidationCode;
import hrpg.server.common.resource.exception.ValidationError;
import hrpg.server.common.resource.exception.ValidationException;
import hrpg.server.creature.service.CreatureComputor;
import hrpg.server.creature.service.CreatureService;
import hrpg.server.creature.service.exception.CreatureInUseException;
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

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path = "creatures")
@ExposesResourceFor(CreatureResponse.class)
public class CreatureController {

    public static final String COLLECTION_REF = "creatures";

    private final TypedEntityLinks<CreatureResponse> links;
    private final PagedResourcesAssembler<CreatureResponse> pagedResourcesAssembler;

    private final CreatureService creatureService;
    private final CreatureResourceMapper creatureResourceMapper;
    private final CreatureComputor creatureComputor;

    public CreatureController(EntityLinks entityLinks,
                              PagedResourcesAssembler<CreatureResponse> pagedResourcesAssembler,
                              CreatureService creatureService,
                              CreatureResourceMapper creatureResourceMapper,
                              CreatureComputor creatureComputor) {
        this.links = entityLinks.forType(CreatureResponse::getId);
        this.pagedResourcesAssembler = pagedResourcesAssembler;

        this.creatureService = creatureService;
        this.creatureResourceMapper = creatureResourceMapper;
        this.creatureComputor = creatureComputor;
    }

    //todo add put for update creature name

    @GetMapping("{id}")
    public CreatureResponse get(@PathVariable long id, @RequestParam(defaultValue = "true") boolean compute) {
        if (compute) creatureComputor.compute(id);

        return creatureService.findById(id)
                .map(creatureResourceMapper::toResponse)
                .map(response -> response.add(links.linkToItemResource(response)))
                .orElseThrow(ResourceNotFoundException::new);
    }

    @GetMapping
    @SortValues(values = {"id"})
    public PagedModel<EntityModel<CreatureResponse>> search(CreatureQueryParams queryParams,
                                                            @RequestParam(defaultValue = "true") boolean compute,
                                                            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        if (compute) creatureComputor.compute();

        Page<CreatureResponse> responses = creatureService.search(creatureResourceMapper.toSearch(queryParams), pageable)
                .map(creatureResourceMapper::toResponse)
                .map(response -> response.add(links.linkToItemResource(response)));
        return pagedResourcesAssembler.toModel(responses);
    }

    @DeleteMapping("{id}")
    public CreatureDeleteResponse delete(@PathVariable long id) {
        try {
            return CreatureDeleteResponse.builder().coins(creatureService.delete(id)).build();
        } catch (CreatureNotFoundException e) {
            throw new ResourceNotFoundException();
        } catch (CreatureInUseException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("creatures.id").code(ValidationCode.CONFLICT.getCode())
                            .value(Optional.ofNullable(e.getId()).map(Object::toString).orElse(null)).build()));
        }
    }
}
