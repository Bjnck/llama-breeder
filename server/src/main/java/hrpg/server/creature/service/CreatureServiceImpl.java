package hrpg.server.creature.service;

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

import static hrpg.server.creature.type.CreatureConstant.*;

@Service
public class CreatureServiceImpl implements CreatureService {

    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;
    private final CreaturesProperties creaturesProperties;
    private final UserService userService;
    private final CreatureFactory creatureFactory;
    private final PenRepository penRepository;

    public CreatureServiceImpl(CreatureRepository creatureRepository,
                               CreatureMapper creatureMapper,
                               ParametersProperties parametersProperties,
                               UserService userService,
                               CreatureFactory creatureFactory,
                               PenRepository penRepository) {
        this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
        this.creaturesProperties = parametersProperties.getCreatures();
        this.userService = userService;
        this.creatureFactory = creatureFactory;
        this.penRepository = penRepository;
    }

    @Transactional(rollbackFor = CreatureNotFoundException.class)
    @Override
    public CreatureDto update(long id, @NotNull CreatureDto creatureDto) throws CreatureNotFoundException {
        Creature creature = creatureRepository.findById(id).orElseThrow(CreatureNotFoundException::new);
        creature.setName(creatureDto.getName());
        return creatureMapper.toDto(creature, userService);
    }

    @Override
    public Optional<CreatureDto> findById(long id) {
        return creatureRepository.findById(id).map(creature -> creatureMapper.toDto(creature, userService));
    }

    @Override
    public long count() {
        return creatureRepository.count();
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
        Creature creature = creatureRepository.findById(id).orElseThrow(() -> new CreatureNotFoundException(id));
        if (penRepository.existsByCreaturesContaining(creature)) throw new CreatureInUseException(creature.getId());

        //sell creature
        int price;
        if (creature.getDetails().isWild() || creature.getInfo().getColor2() == null) price = 0;
        else price = creaturesProperties.getPrice(creature.getGeneration());
        userService.addCoins(price);

        //delete creature
        creatureRepository.delete(creature);

        return price;
    }

    @Transactional
    @Override
    public CreatureDto hit(long id, @NotNull ItemCode itemCode, int itemQuality) throws CreatureNotFoundException {
        Creature creature = creatureRepository.findById(id).orElseThrow(() -> new CreatureNotFoundException(id));

        if (CreatureUtil.isHittable(creature, itemCode)) {
            //remove energy
            creature.getDetails().setEnergy(creature.getDetails().getEnergy() - ENERGY_DIVIDER);
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
                        int increaseLevel = Math.min(
                                Math.min(
                                        increaseLevel(creature.getGeneration(), itemQuality),
                                        creature.getDetails().getHunger() - (STATS_LOVE_REQUIREMENT - 1)),
                                creature.getDetails().getThirst() - (STATS_LOVE_REQUIREMENT - 1));

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
        //max 3 itemQuality above generation effectiveness
        return creaturesProperties.getStatsIncrement(generation) + Math.max(0, Math.min(3, itemQuality - generation));
    }

    private int increaseStat(int stat, int increaseLevel) {
        return Math.min(stat + increaseLevel, STATS_MAX);
    }

    //todo send to computor
    @Transactional(rollbackFor = CreatureNotFoundException.class)
    @Override
    public void calculateEnergy(List<Long> ids) throws CreatureNotFoundException {
        for (long id : ids) {
            Creature creature = creatureRepository.findById(id).orElseThrow(CreatureNotFoundException::new);

            long duration = DurationUtil.getDurationDividedBy(
                    creature.getDetails().getEnergyUpdateTime(),
                    ZonedDateTime.now(),
                    creaturesProperties.getEnergyTimeValue(),
                    creaturesProperties.getEnergyTimeUnit());
            long energyToAdd = duration * (creaturesProperties.getEnergyIncrement(creature.getGeneration()));
            if (energyToAdd > 0) {
                long energy = creature.getDetails().getEnergy() + energyToAdd;
                //add the exact amount of time calculated to not loose started minute
                creature.getDetails().setEnergyUpdateTime(creature.getDetails().getEnergyUpdateTime()
                        .plus(duration * creaturesProperties.getEnergyTimeValue(),
                                creaturesProperties.getEnergyTimeUnit()));
                creature.getDetails().setEnergy(Math.min(ENERGY_MAX, ((Long) energy).intValue()));
            }
        }
    }

    //todo send to computor
    @Transactional(rollbackFor = CreatureNotFoundException.class)
    @Override
    public List<CreatureDto> calculateBirth(List<Long> ids) throws CreatureNotFoundException {
        List<CreatureDto> babies = new ArrayList<>();

        for (long id : ids) {
            Creature creature = creatureRepository.findById(id).orElseThrow(CreatureNotFoundException::new);
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
