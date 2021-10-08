package hrpg.server.pen.service;

import hrpg.server.common.properties.CreaturesProperties;
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
import hrpg.server.creature.type.Sex;
import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.item.service.ItemMapper;
import hrpg.server.item.service.exception.ItemInUseException;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.service.exception.MaxItemsException;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.pen.service.exception.InvalidPenSizeException;
import hrpg.server.pen.service.exception.PenNotFoundException;
import hrpg.server.user.service.UserService;
import hrpg.server.user.service.exception.InsufficientCoinsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PenServiceImpl implements PenService {

    private final PenRepository penRepository;
    private final PenMapper penMapper;
    private final ItemMapper itemMapper;
    private final CreatureRepository creatureRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final CreatureService creatureService;
    private final PensProperties pensProperties;
    private final CreaturesProperties creaturesProperties;

    public PenServiceImpl(PenRepository penRepository,
                          PenMapper penMapper,
                          ItemMapper itemMapper,
                          CreatureRepository creatureRepository,
                          ItemRepository itemRepository,
                          UserService userService,
                          CreatureService creatureService,
                          ParametersProperties parametersProperties) {
        this.penRepository = penRepository;
        this.penMapper = penMapper;
        this.itemMapper = itemMapper;
        this.creatureRepository = creatureRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.creatureService = creatureService;
        this.pensProperties = parametersProperties.getPens();
        this.creaturesProperties = parametersProperties.getCreatures();
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
        //max size is 20
        if (size > 20) size = 20;

        if (size < pen.getSize()) throw new InvalidPenSizeException();
        else if (size > pen.getSize()) {
            int priceForUpdate = 0;
            for (int i = pen.getSize() + 1; i <= size; i++) {
                priceForUpdate += pensProperties.getPrice().get("size-" + i);
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
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
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

        Set<Creature> creatures = new HashSet<>();
        for (Long creatureId : creaturesIds) {
            Creature creature = creatureRepository.findByIdAlive(creatureId)
                    .orElseThrow(() -> new CreatureNotFoundException(creatureId));
            if (penRepository.existsByCreaturesContainingAndIdNot(creature, pen.getId()))
                throw new CreatureInUseException(creatureId);
            //reset penActivationTime to added creature
            creature.getDetails().setPenActivationTime(ZonedDateTime.now());
            creatures.add(creature);
        }

        //reset energyUpdateTime to removed creature
        final Collection<Long> finalCreatureIds = Collections.unmodifiableCollection(creaturesIds);
        pen.getCreatures().stream()
                .filter(creature -> !finalCreatureIds.contains(creature.getId()))
                .forEach(creature -> creature.getDetails().setEnergyUpdateTime(ZonedDateTime.now()));

        //update creatures
        pen.setCreatures(creatures);
    }

    @Transactional(rollbackFor = {
            PenNotFoundException.class,
            ItemNotFoundException.class
    })
    @Override
    public PenActivationDto activateItem(long id, long itemId)
            throws PenNotFoundException, ItemNotFoundException {
        Pen pen = penRepository.findById(id).orElseThrow(PenNotFoundException::new);
        if (pen.getItems().stream().noneMatch(item -> item.getId().equals(itemId)))
            throw new ItemNotFoundException();
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        Set<CreatureDto> creatures = new HashSet<>();

        for (Creature creature : pen.getCreatures()) {
            //only activate item if it is still alive and hittable
            if (item.getLife() > 0 && CreatureUtil.isHittable(creature, item.getCode())) {
                //item hits creature
                if (new Random().nextInt(100) < getActivationChance(creature.getGeneration())) {
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

        return PenActivationDto.builder()
                .item(itemMapper.toDto(item))
                .creatures(creatures)
                .build();
    }

    private int getActivationChance(int creatureGeneration) {
        //activation chance are lower by generation
        return pensProperties.getItemActivationChance() / creatureGeneration;
    }

    @Transactional(rollbackFor = PenNotFoundException.class)
    @Override
    public void breed(long id, ZonedDateTime breedingDate) throws PenNotFoundException {
        Pen pen = penRepository.findById(id).orElseThrow(PenNotFoundException::new);

        List<Creature> breedableCreatures = pen.getCreatures().stream()
                .filter(CreatureUtil::isBreedable)
                .collect(Collectors.toList());

        List<Creature> females = breedableCreatures.stream()
                .filter(creature -> creature.getSex().equals(Sex.F))
                .collect(Collectors.toList());
        Collections.shuffle(females);
        List<Creature> males = breedableCreatures.stream()
                .filter(creature -> creature.getSex().equals(Sex.M))
                .collect(Collectors.toList());
        Collections.shuffle(males);

        int maxBreedings = Math.min(females.size(), males.size());
        for (int i = 0; i < maxBreedings; i++) {
            breed(males.get(i), females.get(i), breedingDate);
        }
    }

    private void breed(Creature male, Creature female, ZonedDateTime breedingDate) {
        //update stats
        male.getDetails().setEnergy(0);
        female.getDetails().setEnergy(0);
        male.getDetails().setThirst(0);
        female.getDetails().setThirst(0);
        male.getDetails().setHunger(0);
        female.getDetails().setHunger(0);
        male.getDetails().setLove(0);
        female.getDetails().setLove(0);

        //increase count breeding
        male.getDetails().setBreedingCount(male.getDetails().getBreedingCount() + 1);
        female.getDetails().setBreedingCount(female.getDetails().getBreedingCount() + 1);

        //set pregnant
        female.getDetails().setPregnant(true);
        female.getDetails().setPregnancyMaleId(male.getId());
        female.getDetails().setPregnancyStartTime(breedingDate);
        //female generation has an impact on the pregnancy time
        female.getDetails().setPregnancyEndTime(
                breedingDate.plus(creaturesProperties.getPregnancyTimeValue() * female.getGeneration(),
                        creaturesProperties.getPregnancyTimeUnit()));
    }
}
