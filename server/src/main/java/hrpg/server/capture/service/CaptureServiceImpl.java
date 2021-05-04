package hrpg.server.capture.service;

import hrpg.server.capture.service.exception.NestUnavailableException;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.item.service.ItemDto;
import hrpg.server.item.service.ItemSearch;
import hrpg.server.item.service.ItemService;
import hrpg.server.item.type.ItemCode;
import hrpg.server.user.service.UserService;
import hrpg.server.user.service.exception.TooManyCaptureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class CaptureServiceImpl implements CaptureService {

//    private final CaptureRepository captureRepository;
//    private final CaptureMapper captureMapper;
    private final ItemService itemService;
    private final UserService userService;
    private final ParametersProperties parametersProperties;

    public CaptureServiceImpl(
//            CaptureRepository captureRepository,
//                              CaptureMapper captureMapper,
                              ItemService itemService,
                              UserService userService,
                              ParametersProperties parametersProperties) {
//        this.captureRepository = captureRepository;
//        this.captureMapper = captureMapper;
        this.itemService = itemService;
        this.userService = userService;
        this.parametersProperties = parametersProperties;
    }

    @Transactional(rollbackFor = {
            TooManyCaptureException.class,
            NestUnavailableException.class
    })
    @Override
    public CaptureDto create(int quality) throws TooManyCaptureException, NestUnavailableException {
        userService.flagCapture(true);

        if (quality > 0) {
            ItemDto nest = itemService.search(
                    ItemSearch.builder().code(ItemCode.NEST).quality(quality).build(),
                    PageRequest.of(0, 1))
                    .stream().findAny().orElseThrow(NestUnavailableException::new);
            itemService.delete(nest.getId());
        }

        Instant current = Instant.now();
//        return captureMapper.toDto(captureRepository.save(Capture.builder()
//                .quality(quality)
//                .startTime(current)
//                .endTime(current.plus(parametersProperties.getCaptures().getTime(), ChronoUnit.SECONDS))
//                .build()));
        return null;
    }

    @Transactional
    @Override
    public void updateFlagAndFillEmpty() {
//        UserDto userDto = userService.get();
//        if (userDto.isCapture() && this.findRunning().isEmpty()) {
//            this.findEmpty().map(this::fill);
//            try {
//                userService.flagCapture(false);
//            } catch (TooManyCaptureException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

//    private Optional<Capture> findEmpty() {
//        return captureRepository.findByCreatureIdIsNullAndUserId(OAuthUserUtil.getUserId());
//    }
//
//    private Capture fill(Capture capture) {
//        if (StringUtils.isNotBlank(capture.getCreatureId()))
//            return capture;
//        //todo generate creature
//        capture.setCreatureId("todo");
//        return captureRepository.save(capture);
//    }

    @Override
    public Optional<CaptureDto> findById(String id) {
        return null;
//        return captureRepository.findById(id).map(captureMapper::toDto);
    }

    @Override
    public Page<CaptureDto> search(Pageable pageable) {
        return null;
//        return captureRepository.findAllWithUserId(Example.of(new Capture()), pageable)
//                .map(captureMapper::toDto);
    }

    @Override
    public Optional<CaptureDto> findRunning() {
        Instant current = Instant.now();
        return null;
//        return captureRepository.findByStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndUserId(
//                current, current, OAuthUserUtil.getUserId())
//                .map(captureMapper::toDto);
    }
}
