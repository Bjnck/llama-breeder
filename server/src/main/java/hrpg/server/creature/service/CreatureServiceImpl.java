package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.dao.CreatureSpecification;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Creature creature = creatureRepository.findById(id).orElseThrow(CreatureNotFoundException::new);

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
}
