package hrpg.server.capture.service;

import hrpg.server.capture.dao.Capture;
import hrpg.server.capture.dao.CaptureRepository;
import hrpg.server.capture.service.exception.BaitUnavailableException;
import hrpg.server.capture.service.exception.NetUnavailableException;
import hrpg.server.capture.service.exception.RunningCaptureException;
import hrpg.server.common.exception.ConflictException;
import hrpg.server.common.properties.CapturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.service.CreatureService;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
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
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class CaptureServiceImpl implements CaptureService {

    private final CaptureRepository captureRepository;
    private final UserRepository userRepository;
    private final CaptureMapper captureMapper;
    private final ItemService itemService;
    private final BaitService baitService;
    private final CreatureService creatureService;
    private final CapturesProperties capturesProperties;

    public CaptureServiceImpl(CaptureRepository captureRepository,
                              UserRepository userRepository,
                              CaptureMapper captureMapper,
                              ItemService itemService,
                              BaitService baitService,
                              CreatureService creatureService,
                              ParametersProperties parametersProperties) {
        this.captureRepository = captureRepository;
        this.userRepository = userRepository;
        this.captureMapper = captureMapper;
        this.itemService = itemService;
        this.baitService = baitService;
        this.creatureService = creatureService;
        this.capturesProperties = parametersProperties.getCaptures();
    }

    @Transactional(rollbackFor = {
            NetUnavailableException.class,
            RunningCaptureException.class,
            BaitUnavailableException.class
    })
    @Override
    public CaptureDto create(int quality, Integer baitGeneration)
            throws NetUnavailableException, RunningCaptureException, BaitUnavailableException {
        //validate net with quality is available and delete it
        if (quality > 0) {
            ItemDto net = itemService.search(
                    ItemSearch.builder().code(ItemCode.NET).quality(quality).build(),
                    PageRequest.of(0, 1))
                    .stream().findAny().orElseThrow(NetUnavailableException::new);
            try {
                itemService.delete(net.getId());
            } catch (ItemNotFoundException e) {
                //item found already deleted -> conflict with other operation
                throw new ConflictException();
            }

            //validate bait is available and decrease it
            if (baitGeneration != null)
                baitService.decreaseCount(baitGeneration);
        } else {
            //can't use bait if no quality
            baitGeneration = null;
        }

        //validate no running capture
        if (hasRunningCapture())
            throw new RunningCaptureException();

        //delete oldest capture if max reached
        long captureCount = captureRepository.count();
        if (captureCount >= capturesProperties.getMax())
            captureRepository.findAll(PageRequest.of(1,
                    capturesProperties.getMax() - 1, Sort.by("id").descending()))
                    .stream().forEach(this::delete);

        //create new capture
        User user = userRepository.get();
        user.getDetails().setLastCapture(Instant.now());

        ZonedDateTime current = ZonedDateTime.now();
        ZonedDateTime endTime;
        if (user.getDetails().getLevel() <= 0)
            endTime = current.plus(capturesProperties.getTimeValueFirst(), capturesProperties.getTimeUnitFirst());
        else {
            //capture twice as long if net used
            int timeValue = quality > 0 ? capturesProperties.getTimeValue() * 2 : capturesProperties.getTimeValue();
            endTime = current.plus(timeValue, capturesProperties.getTimeUnit());
        }

        return captureMapper.toDto(captureRepository.save(Capture.builder()
                .quality(quality)
                .baitGeneration(baitGeneration)
                .startTime(current)
                .endTime(endTime)
                .build()));
    }

    private void delete(Capture capture) {
        //todo if creature has no details, delete creature
        Long creatureId = capture.getCreatureId();
        captureRepository.delete(capture);
        if (creatureId != null) {
            try {
                creatureService.deletePartial(creatureId);
            } catch (CreatureNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Optional<CaptureDto> findById(long id) {
        return captureRepository.findById(id).map(captureMapper::toDto);
    }

    @Override
    public Page<CaptureDto> search(Pageable pageable) {
        return captureRepository.findAll(pageable).map(captureMapper::toDto);
    }

    @Override
    public boolean hasRunningCapture() {
        return captureRepository.countByCreatureIdIsNull() > 0;
    }
}
