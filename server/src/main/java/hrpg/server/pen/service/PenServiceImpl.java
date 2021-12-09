package hrpg.server.pen.service;

import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.properties.PensProperties;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.service.CreatureDto;
import hrpg.server.creature.service.CreatureService;
import hrpg.server.creature.service.CreatureUtil;
import hrpg.server.creature.service.exception.CreatureInUseException;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.item.service.ItemMapper;
import hrpg.server.item.service.exception.ItemInUseException;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.service.exception.MaxItemsException;
import hrpg.server.item.type.ItemCode;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.pen.service.exception.*;
import hrpg.server.user.service.UserService;
import hrpg.server.user.service.exception.InsufficientCoinsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class PenServiceImpl implements PenService {
    private static final int MAX_SIZE = 6;

    private final PenRepository penRepository;
    private final PenMapper penMapper;
    private final ItemMapper itemMapper;
    private final CreatureRepository creatureRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CreatureService creatureService;
    private final PensProperties pensProperties;
    private final PenInfoService penInfoService;

    public PenServiceImpl(PenRepository penRepository,
                          PenMapper penMapper,
                          ItemMapper itemMapper,
                          CreatureRepository creatureRepository,
                          ItemRepository itemRepository,
                          UserService userService,
                          CreatureService creatureService,
                          ParametersProperties parametersProperties,
                          PenInfoService penInfoService) {
        this.penRepository = penRepository;
        this.penMapper = penMapper;
        this.itemMapper = itemMapper;
        this.creatureRepository = creatureRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.creatureService = creatureService;
        this.pensProperties = parametersProperties.getPens();
        this.penInfoService = penInfoService;
    }

    @Override
    public Optional<PenDto> findById(long id) {
        return penRepository.findById(id).map(penMapper::toDto);
    }

    @Override
    public Page<PenDto> search(@NotNull Pageable pageable) {
        return penRepository.findAll(pageable).map(penMapper::toDto);
    }

    @Transactional(rollbackFor = {
            TooManyPenException.class,
            PenNotFullyExtendedException.class,
            InsufficientCoinsException.class,
            InsufficientCoinsException.class
    })
    @Override
    public PenDto create() throws TooManyPenException, PenNotFullyExtendedException, InsufficientCoinsException {
        Page<Pen> pens = penRepository.findAll(Pageable.unpaged());
        //max 2 pens
        if (pens.getTotalElements() > 1) throw new TooManyPenException();
        //first pen must be fully extend
        if (pens.get().findFirst().orElseThrow().getSize() < MAX_SIZE) throw new PenNotFullyExtendedException();

        try {
            int purchasePrice = penInfoService.getPurchasePrice(((Long) (pens.getTotalElements() + 1)).intValue());
            userService.removeCoins(purchasePrice);
        } catch (PriceNotFoundException e) {
            throw new RuntimeException(e);
        }

        Pen pen = Pen.builder().build();
        pen.setUserId(userService.get().getId());
        return penMapper.toDto(penRepository.save(pen));
    }

    @Transactional(rollbackFor = {
            PenNotFoundException.class,
            InvalidPenSizeException.class,
            InsufficientCoinsException.class,
            ItemInUseException.class,
            MaxItemsException.class,
            ItemNotFoundException.class,
            CreatureNotFoundException.class,
            MaxCreaturesException.class,
            CreatureInUseException.class
    })
    @Override
    public PenDto update(long id, @NotNull PenDto penDto)
            throws PenNotFoundException, InvalidPenSizeException, InsufficientCoinsException, ItemInUseException,
            MaxItemsException, ItemNotFoundException, CreatureNotFoundException, MaxCreaturesException, CreatureInUseException {
        Pen pen = penRepository.findById(id).orElseThrow(PenNotFoundException::new);
        updateSize(pen, penDto.getSize());
        updateItems(pen, penDto.getItemIds());
        updateCreatures(pen, penDto.getCreatureIds());
        return penMapper.toDto(pen);
    }

    private void updateSize(Pen pen, int size) throws InvalidPenSizeException, InsufficientCoinsException {
        if (size > MAX_SIZE) size = MAX_SIZE;

        if (size < pen.getSize()) throw new InvalidPenSizeException();
        else if (size > pen.getSize()) {
            int penCount = ((Long) penRepository.countBySize(MAX_SIZE)).intValue() + 1;
            int priceForUpdate = 0;
            for (int i = pen.getSize() + 1; i <= size; i++) {
                try {
                    priceForUpdate += penInfoService.getExtendPrice(penCount, i);
                } catch (PriceNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            userService.removeCoins(priceForUpdate);
            pen.setSize(size);
        }
    }

    private void updateItems(Pen pen, Set<Long> itemIds)
            throws ItemNotFoundException, ItemInUseException, MaxItemsException {
        if (itemIds == null) itemIds = new HashSet<>();

        if (pen.getSize() < itemIds.size())
            throw new MaxItemsException();

        Set<Item> items = new HashSet<>();
        for (Long itemId : itemIds) {
            Item item = itemRepository.findById(itemId)
                    .filter(i -> !ItemCode.NET.equals(i.getCode()))
                    .orElseThrow(() -> new ItemNotFoundException(itemId));
            if (penRepository.existsByItemsContainingAndIdNot(item, pen.getId())) throw new ItemInUseException(itemId);
            //reset penActivationTime to added item
            item.setPenActivationTime(ZonedDateTime.now());
            items.add(item);
        }
        pen.setItems(items);
    }

    private void updateCreatures(Pen pen, Set<Long> creaturesIds)
            throws MaxCreaturesException, CreatureNotFoundException, CreatureInUseException {
        if (creaturesIds == null) creaturesIds = new HashSet<>();

        if (pen.getSize() < creaturesIds.size())
            throw new MaxCreaturesException();

        ZonedDateTime now = ZonedDateTime.now();

        Set<Creature> creatures = new HashSet<>();
        for (Long creatureId : creaturesIds) {
            Creature creature = creatureRepository.findById(creatureId)
                    .orElseThrow(() -> new CreatureNotFoundException(creatureId));
            if (penRepository.existsByCreaturesContainingAndIdNot(creature, pen.getId()))
                throw new CreatureInUseException(creatureId);

            //reset penActivationTime to newly added creature
            if (pen.getCreatures().stream().noneMatch(c -> c.getId().equals(creatureId)))
                creature.setPenActivationTime(now);

            creatures.add(creature);
        }


        final Collection<Long> finalCreatureIds = Collections.unmodifiableCollection(creaturesIds);
        //reset energyUpdateTime to removed creature
        pen.getCreatures().stream()
                .filter(creature -> !finalCreatureIds.contains(creature.getId()))
                .forEach(creature -> creature.setEnergyUpdateTime(now));

        //update creatures
        pen.setCreatures(creatures);
    }

    @Transactional(rollbackFor = {
            PenNotFoundException.class,
            ItemNotFoundException.class
    })
    @Override
    public PenActivationDto activateItem(long id, long itemId) throws PenNotFoundException, ItemNotFoundException {
        return activateItem(id, itemId, null);
    }

    @Transactional(rollbackFor = {
            PenNotFoundException.class,
            ItemNotFoundException.class
    })
    @Override
    public PenActivationDto activateItem(long id, long itemId, ZonedDateTime activationTime)
            throws PenNotFoundException, ItemNotFoundException {
        Pen pen = penRepository.findById(id).orElseThrow(PenNotFoundException::new);
        if (pen.getItems().stream().noneMatch(item -> item.getId().equals(itemId)))
            throw new ItemNotFoundException();
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        Set<CreatureDto> creatures = new HashSet<>();

        //if activationTime is not null, check that item and creature are present in the pen at that time
        if (activationTime == null || item.getPenActivationTime().isBefore(activationTime)) {
            pen.getCreatures().stream().sorted(Comparator.comparingLong(Creature::getId)).forEach(
                    creature -> {
                        if (activationTime == null || creature.getPenActivationTime().isBefore(activationTime)) {
                            //only activate item if it is still alive and hittable
                            if (item.getLife() > 0 && CreatureUtil.isHittable(creature, item.getCode(), item.getQuality())) {
                                //item hits creature
                                if (new Random().nextInt(100) < pensProperties.getItemActivationChance()) {
                                    //remove 1 life and delete if life ended
                                    item.setLife(item.getLife() - 1);
                                    //hit creature
                                    try {
                                        creatures.add(creatureService.hit(creature.getId(), item.getCode(), item.getQuality()));
                                    } catch (CreatureNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }
                    }
            );
        }

        return PenActivationDto.builder()
                .item(itemMapper.toDto(item))
                .creatures(creatures)
                .build();
    }
}
