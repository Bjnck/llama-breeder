package hrpg.server.capture.service;

import hrpg.server.capture.dao.Capture;
import hrpg.server.capture.dao.CaptureRepository;
import hrpg.server.capture.service.exception.NestUnavailableException;
import hrpg.server.capture.service.exception.RunningCaptureException;
import hrpg.server.common.exception.ConflictException;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.item.service.ItemDto;
import hrpg.server.item.service.ItemSearch;
import hrpg.server.item.service.ItemService;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.type.ItemCode;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CaptureServiceImpl implements CaptureService {

    private final CaptureRepository captureRepository;
    private final UserRepository userRepository;
    private final CaptureMapper captureMapper;
    private final ItemService itemService;
    private final ParametersProperties parametersProperties;

    public CaptureServiceImpl(CaptureRepository captureRepository,
                              UserRepository userRepository,
                              CaptureMapper captureMapper,
                              ItemService itemService,
                              ParametersProperties parametersProperties) {
        this.captureRepository = captureRepository;
        this.userRepository = userRepository;
        this.captureMapper = captureMapper;
        this.itemService = itemService;
        this.parametersProperties = parametersProperties;
    }

    @Transactional(rollbackFor = {
            NestUnavailableException.class,
            RunningCaptureException.class
    })
    @Override
    public CaptureDto create(int quality) throws NestUnavailableException, RunningCaptureException {
        //validate nest with quality is available and delete it
        if (quality > 0) {
            ItemDto nest = itemService.search(
                    ItemSearch.builder().code(ItemCode.NEST).quality(quality).build(),
                    PageRequest.of(0, 1))
                    .stream().findAny().orElseThrow(NestUnavailableException::new);
            try {
                itemService.delete(nest.getId());
            } catch (ItemNotFoundException e) {
                //item found already deleted -> conflict with other operation
                throw new ConflictException();
            }
        }

        //validate no running capture
        if (hasRunningCapture())
            throw new RunningCaptureException();


        //delete oldest capture if max reached
        long captureCount = userRepository.count();
        if (captureCount >= parametersProperties.getCaptures().getMax()) {
            captureRepository.findAll(PageRequest.of(0, 1, Sort.by("id").ascending()))
                    .stream()
                    .findFirst()
                    .ifPresent(captureRepository::delete);
        }

        //create new capture
        User user = userRepository.get();
        user.getDetails().setLastCapture(Instant.now());

        LocalDateTime current = LocalDateTime.now();
        return captureMapper.toDto(captureRepository.save(Capture.builder()
                .quality(quality)
                .startTime(current)
                .endTime(current.plus(
                        parametersProperties.getCaptures().getTimeValue(),
                        parametersProperties.getCaptures().getTimeUnit()))
                .build()));
    }

    @Override
    public Optional<CaptureDto> findById(Long id) {
        return captureRepository.findById(id).map(captureMapper::toDto);
    }

    @Override
    public Page<CaptureDto> search(Pageable pageable) {
        return captureRepository.findAll(pageable).map(captureMapper::toDto);
    }

    @Override
    public boolean hasRunningCapture() {
        LocalDateTime current = LocalDateTime.now();
        return captureRepository.countByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(current, current) > 0;
    }
}
