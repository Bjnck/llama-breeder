package hrpg.server.capture.resource;

import hrpg.server.capture.service.CaptureComputor;
import hrpg.server.capture.service.CaptureDto;
import hrpg.server.capture.service.CaptureService;
import hrpg.server.capture.service.exception.BaitUnavailableException;
import hrpg.server.capture.service.exception.NetUnavailableException;
import hrpg.server.capture.service.exception.RunningCaptureException;
import hrpg.server.common.resource.SortValues;
import hrpg.server.common.resource.exception.ResourceNotFoundException;
import hrpg.server.common.resource.exception.ValidationError;
import hrpg.server.common.resource.exception.ValidationException;
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
import javax.validation.constraints.NotNull;
import java.util.Collections;

@RestController
@RequestMapping(path = "captures")
@ExposesResourceFor(CaptureResponse.class)
public class CaptureController {

    public static final String COLLECTION_REF = "captures";

    private final TypedEntityLinks<CaptureResponse> links;
    private final PagedResourcesAssembler<CaptureResponse> pagedResourcesAssembler;

    private final CaptureService captureService;
    private final CaptureResourceMapper captureResourceMapper;
    private final CaptureComputor captureComputor;

    public CaptureController(EntityLinks entityLinks,
                             PagedResourcesAssembler<CaptureResponse> pagedResourcesAssembler,
                             CaptureService captureService,
                             CaptureResourceMapper captureResourceMapper,
                             CaptureComputor captureComputor) {
        this.links = entityLinks.forType(CaptureResponse::getId);
        this.pagedResourcesAssembler = pagedResourcesAssembler;

        this.captureService = captureService;
        this.captureResourceMapper = captureResourceMapper;
        this.captureComputor = captureComputor;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CaptureRequest request) {
        captureComputor.compute();

        CaptureResponse response;
        try {
            response = captureResourceMapper.toResponse(captureService.create(request.getQuality(), request.getBait()));
        } catch (RunningCaptureException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("_self").code("runningCapture").build()));
        } catch (NetUnavailableException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("quality").code("unavailable").build()));
        } catch (BaitUnavailableException e) {
            throw new ValidationException(Collections.singletonList(
                    ValidationError.builder().field("bait").code("unavailable").build()));
        }
        return ResponseEntity.created(links.linkToItemResource(response).toUri())
                .header("Access-Control-Expose-Headers", HttpHeaders.LOCATION).build();
    }

    @GetMapping("{id}")
    public CaptureResponse get(@PathVariable long id) {
        captureComputor.compute(id);
        return toResponse(captureService.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @GetMapping
    @SortValues(values = {"id", "startTime", "endTime"})
    public PagedModel<EntityModel<CaptureResponse>> search(
            @SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        captureComputor.compute();
        Page<CaptureResponse> captures = captureService.search(pageable).map(this::toResponse);
        return pagedResourcesAssembler.toModel(captures);
    }

    private CaptureResponse toResponse(@NotNull CaptureDto captureDto) {
        CaptureResponse response = captureResourceMapper.toResponse(captureDto);
        response.add(links.linkToItemResource(response));

        return response;
    }
}
