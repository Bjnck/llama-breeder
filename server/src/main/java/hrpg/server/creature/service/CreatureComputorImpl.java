package hrpg.server.creature.service;

import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.pen.service.PenComputor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hrpg.server.creature.type.CreatureConstant.ENERGY_MAX;

@Component
public class CreatureComputorImpl implements CreatureComputor {

    private final CreatureRepository creatureRepository;
    private final PenRepository penRepository;
    private final PenComputor penComputor;
    private final CreatureService creatureService;

    public CreatureComputorImpl(CreatureRepository creatureRepository,
                                PenRepository penRepository,
                                PenComputor penComputor,
                                CreatureService creatureService) {
        this.creatureRepository = creatureRepository;
        this.penRepository = penRepository;
        ;
        this.penComputor = penComputor;
        this.creatureService = creatureService;
    }

    @Override
    public void compute() {
        //calculate energy for creatures in barn
        Pageable pageRequestEnergy = PageRequest.of(0, 20);
        Page<Creature> creaturesEnergy;
        do {
            creaturesEnergy = creatureRepository.findAllByEnergyNotFull(pageRequestEnergy);
            try {
                creatureService.calculateEnergy(getIdNotInPen(creaturesEnergy));
            } catch (CreatureNotFoundException e) {
                throw new RuntimeException(e);
            }
            pageRequestEnergy = pageRequestEnergy.next();
        } while (!creaturesEnergy.isEmpty());

        //update statistics for all creatures in pen
        penComputor.compute();
    }

    private List<Long> getIdNotInPen(Page<Creature> creatures) {
        return creatures.stream()
                .filter(creature -> !penRepository.existsByCreaturesContaining(creature))
                .map(Creature::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void compute(long id) {
        creatureRepository.findById(id).ifPresent(creature -> {
            Optional<Pen> pen = penRepository.findByCreaturesContaining(creature);

            //if creature in pen, update stats with items in pen
            if (pen.isPresent()) {
                penComputor.compute(pen.get().getId());
            }
            //if creature not in pen
            else {
                //update energy
                if (creature.getEnergy() < ENERGY_MAX) {
                    try {
                        creatureService.calculateEnergy(Collections.singletonList(creature.getId()));
                    } catch (CreatureNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
