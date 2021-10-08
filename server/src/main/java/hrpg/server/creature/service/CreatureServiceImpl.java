package hrpg.server.creature.service;

import hrpg.server.capture.dao.CaptureRepository;
import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.util.DurationUtil;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.dao.CreatureSpecification;
import hrpg.server.creature.service.exception.CreatureInUseException;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.item.type.ItemCode;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreatureServiceImpl implements CreatureService {

    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;
    private final CreaturesProperties creaturesProperties;
    private final UserService userService;
    private final CreatureFactory creatureFactory;
    private final PenRepository penRepository;
    private final CaptureRepository captureRepository;

    public CreatureServiceImpl(CreatureRepository creatureRepository,
                               CreatureMapper creatureMapper,
                               ParametersProperties parametersProperties,
                               UserService userService,
                               CreatureFactory creatureFactory,
                               PenRepository penRepository,
                               CaptureRepository captureRepository) {
        this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
        this.creaturesProperties = parametersProperties.getCreatures();
        this.userService = userService;
        this.creatureFactory = creatureFactory;
        this.penRepository = penRepository;
        this.captureRepository = captureRepository;
    }

    @Override
    public Optional<CreatureDto> findById(long id) {
        return creatureRepository.findByIdAlive(id).map(creature -> creatureMapper.toDto(creature, userService));
    }

    @Override
    public long count() {
        return creatureRepository.countAlive();
    }

    @Override
    public Page<CreatureDto> search(CreatureSearch search, Pageable pageable) {
        return creatureRepository.findAll(new CreatureSpecification(creatureMapper.toCriteria(search)), pageable)
                .map(creature -> creatureMapper.toDto(creature, userService));
    }

    @Transactional(rollbackFor = {
            CreatureNotFoundException.class,
            CreatureInUseException.class
    })
    @Override
    public int delete(long id) throws CreatureNotFoundException, CreatureInUseException {
        Creature creature = creatureRepository.findByIdAlive(id).orElseThrow(() -> new CreatureNotFoundException(id));
        if (penRepository.existsByCreaturesContaining(creature)) throw new CreatureInUseException(creature.getId());

        //sell creature
        int price = creaturesProperties.getPrice().get("gen-" + creature.getGeneration());
        userService.addCoins(price);

        //delete creature
        deleteRecursive(creature);

        return price;
    }

    @Transactional(rollbackFor = {
            CreatureNotFoundException.class
    })
    @Override
    public void deletePartial(long id) throws CreatureNotFoundException {
        Creature creature = creatureRepository.findById(id).orElseThrow(() -> new CreatureNotFoundException(id));
        //creature has not been partially deleted
        if (creature.getDetails() == null)
            deleteRecursive(creature);
    }

    private void deleteRecursive(Creature creature) {
        Long parentId1 = creature.getParentId1();
        Long parentId2 = creature.getParentId2();

        //partial delete if has children
        boolean hasCapture = captureRepository.existsByCreatureId(creature.getId());
        long childCount = creatureRepository.countByParentId1OrParentId2(creature.getId(), creature.getId());
        if (childCount > 0 || hasCapture) {
            creature.setDetails(null);
            creature.setParentId1(null);
            creature.setParentId2(null);
        } else
            creatureRepository.delete(creature);

        //if parent partially deleted, clean parent if has no more children
        if (parentId1 != null) {
            Creature parent = creatureRepository.findById(parentId1).orElseThrow();
            if (parent.getDetails() == null) {
                deleteRecursive(parent);
            }
        }
        if (parentId2 != null) {
            Creature parent = creatureRepository.findById(parentId2).orElseThrow();
            if (parent.getDetails() == null) {
                deleteRecursive(parent);
            }
        }
    }

    @Transactional
    @Override
    public CreatureDto hit(long id, @NotNull ItemCode itemCode, int itemQuality) throws CreatureNotFoundException {
        Creature creature = creatureRepository.findByIdAlive(id).orElseThrow(() -> new CreatureNotFoundException(id));

        if (CreatureUtil.isHittable(creature, itemCode)) {
            //remove energy
            creature.getDetails().setEnergy(creature.getDetails().getEnergy() - 10);
            //check item quality is valid for creature generation
            if (itemQuality >= creature.getGeneration()) {
                //increase stats
                switch (itemCode) {
                    case HUNGER:
                        creature.getDetails().setHunger(increaseStat(creature.getDetails().getHunger(),
                                increaseLevel(creature.getGeneration(), itemQuality)));
                        break;
                    case THIRST:
                        creature.getDetails().setThirst(increaseStat(creature.getDetails().getThirst(),
                                increaseLevel(creature.getGeneration(), itemQuality)));
                        break;
                    case LOVE:
                        //must not increase more than hunger/thirst available
                        int increaseLevel = Math.min(Math.min(Math.min(
                                increaseLevel(creature.getGeneration(), itemQuality), 100),
                                creature.getDetails().getHunger() - 74),
                                creature.getDetails().getThirst() - 74);

                        creature.getDetails().setHunger(creature.getDetails().getHunger() - increaseLevel);
                        creature.getDetails().setThirst(creature.getDetails().getThirst() - increaseLevel);
                        creature.getDetails().setLove(increaseStat(creature.getDetails().getLove(), increaseLevel));
                        break;
                }
            }
        }

        return creatureMapper.toDto(creature, userService);
    }

    private int increaseLevel(int generation, int itemQuality) {
        return 3 + (itemQuality - generation);
    }

    private int increaseStat(int stat, int increaseLevel) {
        return Math.min(stat + increaseLevel, 100);
    }

    @Transactional(rollbackFor = CreatureNotFoundException.class)
    @Override
    public void calculateEnergy(List<Long> ids) throws CreatureNotFoundException {
        for (long id : ids) {
            Creature creature = creatureRepository.findByIdAlive(id).orElseThrow(CreatureNotFoundException::new);

            long duration = DurationUtil.getDurationDividedBy(
                    creature.getDetails().getEnergyUpdateTime(),
                    ZonedDateTime.now(),
                    creaturesProperties.getEnergyTimeValue(),
                    creaturesProperties.getEnergyTimeUnit());
            long energyToAdd = duration * (11 - creature.getGeneration());
            if (energyToAdd > 0) {
                long energy = creature.getDetails().getEnergy() + energyToAdd;
                //add the exact amount of time calculated to not loose started minute
                creature.getDetails().setEnergyUpdateTime(creature.getDetails().getEnergyUpdateTime()
                        .plus(duration * creaturesProperties.getEnergyTimeValue(),
                                creaturesProperties.getEnergyTimeUnit()));
                creature.getDetails().setEnergy(energy > 1000 ? 1000 : ((Long) energy).intValue());
            }
        }
    }

    @Transactional(rollbackFor = CreatureNotFoundException.class)
    @Override
    public List<CreatureDto> calculateBirth(List<Long> ids) throws CreatureNotFoundException {
        List<CreatureDto> babies = new ArrayList<>();

        for (long id : ids) {
            Creature creature = creatureRepository.findByIdAlive(id).orElseThrow(CreatureNotFoundException::new);
            if (creature.getDetails().getPregnancyEndTime().isBefore(ZonedDateTime.now()))
                babies.addAll(getBabies(creature));
        }

        //update user level if new generation discovered
        if (!babies.isEmpty()) {
            int maxGen = Collections.max(babies.stream().map(CreatureDto::getGeneration).collect(Collectors.toList()));
            userService.updateLevel(maxGen);
        }

        return babies;
    }

    private List<CreatureDto> getBabies(Creature creature) throws CreatureNotFoundException {
        try {
            return creatureFactory.generateForBirth(creature.getId());
        } catch (MaxCreaturesException e) {
            return Collections.emptyList();
        }
    }
}
