package hrpg.server.creature.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.pen.dao.PenRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.ZonedDateTime;

@Component
public class CreatureComputorImpl implements CreatureComputor {

    private final CreatureRepository creatureRepository;
    private final PenRepository penRepository;
    private final CreaturesProperties creaturesProperties;

    public CreatureComputorImpl(CreatureRepository creatureRepository,
                                PenRepository penRepository,
                                ParametersProperties parametersProperties) {
        this.creatureRepository = creatureRepository;
        this.penRepository = penRepository;
        this.creaturesProperties = parametersProperties.getCreatures();
    }

    @Transactional
    @Override
    public void compute() {
        Pageable pageRequest = PageRequest.of(0, 20);
        Page<Creature> creatures;
        do {
            creatures = creatureRepository.findAllByEnergyNotFull(pageRequest);
            creatures.get().forEach(this::increaseEnergy);
            pageRequest = pageRequest.next();
        } while (!creatures.isEmpty());
    }

    @Transactional
    @Override
    public void compute(long id) {
        creatureRepository.findByIdAndEnergyNotFull(id).ifPresent(this::increaseEnergy);
    }

    private void increaseEnergy(Creature creature) {
        ZonedDateTime now = ZonedDateTime.now();
        if (!penRepository.existsByCreaturesContaining(creature)) {
            ZonedDateTime energyUpdateTime = creature.getDetails().getEnergyUpdateTime();
            Duration duration = Duration.between(energyUpdateTime, now);
            long energyToAdd = duration.dividedBy(
                    Duration.of(creaturesProperties.getEnergyTimeValue(), creaturesProperties.getEnergyTimeUnit()));
            if (energyToAdd > 0) {
                long energy = creature.getDetails().getEnergy() + energyToAdd;
                creature.getDetails().setEnergyUpdateTime(now);
                creature.getDetails().setEnergy(energy > 100 ? 100 : ((Long) energy).intValue());
            }
        }
    }
}
