package hrpg.server.creature.service;

import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.dao.CreatureSpecification;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreatureServiceImpl implements CreatureService {

    private final CreatureRepository creatureRepository;
    private final CreatureMapper creatureMapper;

    public CreatureServiceImpl(CreatureRepository creatureRepository,
                               CreatureMapper creatureMapper) {
        this.creatureRepository = creatureRepository;
        this.creatureMapper = creatureMapper;
    }

    @Override
    public Optional<CreatureDto> findById(Long id) {
        return creatureRepository.findById(id).map(creatureMapper::toDto);
    }

    @Override
    public Page<CreatureDto> search(CreatureSearch search, Pageable pageable) {
        return creatureRepository.findAll(new CreatureSpecification(creatureMapper.toCriteria(search)), pageable)
                .map(creatureMapper::toDto);
    }

    @Override
    public int delete(long id) throws CreatureNotFoundException {
        //todo sell creature, add coins before delete
        // smart delete: remove details and remove full only if not in use
        // clean child/parent
        Creature creature = creatureRepository.findById(id).orElseThrow(CreatureNotFoundException::new);
        creatureRepository.delete(creature);
        return 0;
    }
}
