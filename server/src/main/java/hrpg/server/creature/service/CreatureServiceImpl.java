package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.dao.CreatureSpecification;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.type.Sex;
import hrpg.server.item.type.ItemCode;
import hrpg.server.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreatureServiceImpl implements CreatureService {

    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;
    private final CreaturesProperties creaturesProperties;
    private final UserService userService;

    public CreatureServiceImpl(CreatureRepository creatureRepository,
                               CreatureMapper creatureMapper,
                               ParametersProperties parametersProperties,
                               UserService userService) {
        this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
        this.creaturesProperties = parametersProperties.getCreatures();
        this.userService = userService;
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

    @Transactional
    @Override
    public int delete(long id) throws CreatureNotFoundException {
        //todo smart delete: remove details and remove full only if not in use
        // clean child/parent
        Creature creature = getCreature(id);

        //sell creature
        int price = getPrice(creature);
        userService.addCoins(price);

        //delete creature
        creatureRepository.delete(creature);
        return price;
    }

    private int getPrice(Creature creature) {
        if (!creature.getDetails().isWild())
            return creaturesProperties.getPrice().get("gene-" + creature.getGeneration());
        return 0;
    }

    //todo service should check if statistics reach a goal and manage reproduction
    @Transactional
    @Override
    public CreatureDto hit(long id, @NotNull ItemCode itemCode, int itemQuality) throws CreatureNotFoundException {
        Creature creature = getCreature(id);
        //check creature has energy
        if (creature.getDetails().getEnergy() > 0) {
            //remove 1 energy
            creature.getDetails().setEnergy(creature.getDetails().getEnergy() - 1);
            //check item quality is valid for creature generation
            //todo if quality > gen then increase stat more than 1
            if (creature.getGeneration() <= itemQuality) {
                //increase stats
                int stat;
                switch (itemCode) {
                    case HUNGER:
                        stat = creature.getDetails().getHunger();
                        creature.getDetails().setHunger(increaseStat(stat));
                        break;
                    case THIRST:
                        stat = creature.getDetails().getThirst();
                        creature.getDetails().setThirst(increaseStat(stat));
                        break;
                    case LOVE:
                        if (creature.getDetails().getHunger() >= 75 && creature.getDetails().getThirst() >= 75 &&
                                creature.getDetails().getMaturity() >= 100) {
                            stat = creature.getDetails().getLove();
                            creature.getDetails().setLove(increaseStat(stat));
                        }
                        break;
                }
            }
        }

        return creatureMapper.toDto(creature, userService);
    }

    private int increaseStat(int stat) {
        return stat < 100 ? stat + 1 : stat;
    }

    @Transactional
    @Override
    public boolean breed(long id1, long id2) throws CreatureNotFoundException {
        Creature creature1 = getCreature(id1);
        Creature creature2 = getCreature(id2);

        if (id1 != id2 && !creature1.getSex().equals(creature2.getSex()) &&
                CreatureUtil.isBreedable(creature1.getDetails().getMaturity(), creature1.getDetails().getLove()) &&
                CreatureUtil.isBreedable(creature2.getDetails().getMaturity(), creature2.getDetails().getLove())) {
            creature1.getDetails().setLove(0);
            creature2.getDetails().setLove(0);

            Creature female = creature1.getSex().equals(Sex.F) ? creature1 : creature2;
            female.getDetails().setPregnant(true);
            female.getDetails().setPregnancyStartTime(LocalDateTime.now());
            return true;
        }
        return false;
    }

    private Creature getCreature(long id) throws CreatureNotFoundException {
        return creatureRepository.findById(id).orElseThrow(() -> new CreatureNotFoundException(id));
    }
}
