package hrpg.server.capture.service;

import hrpg.server.capture.dao.Capture;
import hrpg.server.capture.dao.CaptureRepository;
import hrpg.server.capture.service.exception.BaitUnavailableException;
import hrpg.server.capture.service.exception.CaptureNotFoundException;
import hrpg.server.capture.service.exception.NetUnavailableException;
import hrpg.server.capture.service.exception.RunningCaptureException;
import hrpg.server.common.exception.ConflictException;
import hrpg.server.common.properties.CapturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.service.CreatureDto;
import hrpg.server.creature.service.CreatureFactory;
import hrpg.server.creature.service.exception.MaxCreaturesException;
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
    private final CreatureRepository creatureRepository;
    private final CaptureMapper captureMapper;
    private final ItemService itemService;
    private final BaitService baitService;
    private final CreatureFactory creatureFactory;
    private final CapturesProperties capturesProperties;

    public CaptureServiceImpl(CaptureRepository captureRepository,
                              UserRepository userRepository,
                              CreatureRepository creatureRepository,
                              CaptureMapper captureMapper,
                              ItemService itemService,
                              BaitService baitService,
                              CreatureFactory creatureFactory,
                              ParametersProperties parametersProperties) {
        this.captureRepository = captureRepository;
        this.userRepository = userRepository;
        this.creatureRepository = creatureRepository;
        this.captureMapper = captureMapper;
        this.itemService = itemService;
        this.baitService = baitService;
        this.creatureFactory = creatureFactory;
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
                    .stream().forEach(captureRepository::delete);

        //create new capture
        User user = userRepository.get();
        user.getDetails().setLastCapture(Instant.now());

        ZonedDateTime current = ZonedDateTime.now();
        ZonedDateTime endTime;
        if (user.getDetails().getLevel() <= 0) {
            if (captureCount < 2)
                endTime = current.plus(capturesProperties.getTimeValueFirst(), capturesProperties.getTimeUnitFirst());
            else
                endTime = current.plus(capturesProperties.getTimeValueThird(), capturesProperties.getTimeUnitThird());
        } else {
            endTime = current.plus(capturesProperties.getTimeValue(), capturesProperties.getTimeUnit());
        }

        return captureMapper.toDto(captureRepository.save(Capture.builder()
                .quality(quality)
                .baitGeneration(baitGeneration)
                .startTime(current)
                .endTime(endTime)
                .build()));
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
        return captureRepository.countByCreatureInfoIsNull() > 0;
    }

    @Transactional(rollbackFor = {
            CaptureNotFoundException.class,
            RunningCaptureException.class,
            MaxCreaturesException.class
    })
    @Override
    public CaptureDto redeem(long id) throws CaptureNotFoundException, RunningCaptureException, MaxCreaturesException {
        Capture capture = captureRepository.findById(id).orElseThrow(CaptureNotFoundException::new);
        if (capture.getEndTime().isAfter(ZonedDateTime.now()))
            throw new RunningCaptureException();

        User user = userRepository.get();

        //generate new creature and add info to capture
        CreatureDto creatureDto = creatureFactory.generateForCapture(
                user.getDetails().getLevel(),
                capture.getQuality(),
                capture.getBaitGeneration(),
                capture.getEndTime().toLocalDate());
        Creature creature = creatureRepository.findById(creatureDto.getId()).orElseThrow();
        capture.setCreatureInfo(creature.getInfo().toBuilder().id(null).build());

        //end tutorial if 3 creatures captured
        if (user.getDetails().getLevel() == 0 && captureRepository.count() >= 3)
            user.getDetails().setLevel(1);

        return captureMapper.toDto(capture);
    }
}
