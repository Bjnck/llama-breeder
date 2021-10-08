package hrpg.server.pen.service;

import hrpg.server.common.properties.CreaturesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.properties.PensProperties;
import hrpg.server.common.util.DurationUtil;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.service.CreatureUtil;
import hrpg.server.creature.type.Sex;
import hrpg.server.item.dao.Item;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.type.ItemCode;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.pen.service.exception.PenNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PenComputorImpl implements PenComputor {
    private final PenRepository penRepository;
    private final PenService penService;
    private final PensProperties pensProperties;
    private final CreaturesProperties creaturesProperties;

    public PenComputorImpl(PenRepository penRepository,
                           PenService penService,
                           ParametersProperties parametersProperties) {
        this.penRepository = penRepository;
        this.penService = penService;
        this.pensProperties = parametersProperties.getPens();
        this.creaturesProperties = parametersProperties.getCreatures();
    }

    @Transactional
    @Override
    public void compute() {
        penRepository.findAll(Pageable.unpaged()).forEach(this::compute);
    }

    @Transactional
    @Override
    public void compute(Long id) {
        penRepository.findById(id).ifPresent(this::compute);
    }

    private void compute(Pen pen) {
        ZonedDateTime now = ZonedDateTime.now();

        if (!pen.getCreatures().isEmpty()) {
            ZonedDateTime minActivationTime = pen.getCreatures().stream()
                    .map(creature -> creature.getDetails().getPenActivationTime())
                    .min(Comparator.comparing(dateTime -> dateTime))
                    .orElseThrow();

            long totalLoop = DurationUtil.getDurationDividedBy(minActivationTime, now,
                    pensProperties.getActivationTimeValue(), pensProperties.getActivationTimeUnit());
            for (int i = 0; i < totalLoop; i++) {
                //stop loop if nothing to calculate
                Set<ItemCode> itemsCodes = pen.getItems().stream()
                        .filter(item -> item.getLife() > 0).map(Item::getCode).collect(Collectors.toSet());
                long hittable = pen.getCreatures().stream()
                        .filter(creature -> CreatureUtil.isHittable(creature, itemsCodes)).count();
                long breedableMale = pen.getCreatures().stream()
                        .filter(CreatureUtil::isBreedable)
                        .filter(creature -> Sex.M.equals(creature.getSex()))
                        .count();
                long breedableFemale = pen.getCreatures().stream()
                        .filter(CreatureUtil::isBreedable)
                        .filter(creature -> Sex.F.equals(creature.getSex()))
                        .count();
                boolean adultPresent = pen.getCreatures().stream()
                        .anyMatch(creature -> creature.getDetails().getMaturity() >= 1000);
                long babies = pen.getCreatures().stream()
                        .filter(creature -> creature.getDetails().getMaturity() < 1000).count();
                if (hittable == 0 && (breedableMale == 0 || breedableFemale == 0) && (babies == 0 || !adultPresent))
                    break;

                ZonedDateTime activationTime = minActivationTime.plus(
                        i * pensProperties.getActivationTimeValue(), pensProperties.getActivationTimeUnit());

                //calculate hit based on activationTime
                hit(pen, activationTime);
                //calculate breed based on activationTime
                breed(pen, activationTime);
                //calculate maturity based on activationTime
                increaseMaturity(pen, activationTime);
            }
        }

        //update penActivationTime
        pen.getItems().forEach(item -> item.setPenActivationTime(now));
        pen.getCreatures().forEach(creature -> creature.getDetails().setPenActivationTime(now));
    }

    private void hit(Pen pen, ZonedDateTime activationTime) {
        Set<Item> items = pen.getItems().stream()
                .filter(item -> item.getLife() > 0)
                .filter(item -> item.getPenActivationTime().isBefore(activationTime))
                .collect(Collectors.toSet());
        items.forEach(item -> {
            try {
                penService.activateItem(pen.getId(), item.getId());
            } catch (PenNotFoundException | ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void increaseMaturity(Pen pen, ZonedDateTime activationTime) {
        //verify if adult in pen
        boolean adultPresent = pen.getCreatures().stream().anyMatch(creature -> creature.getDetails().getMaturity() >= 1000);
        if (adultPresent) {
            //get all babies for activationTime
            Set<Creature> creatures = pen.getCreatures().stream()
                    .filter(creature -> creature.getDetails().getMaturity() < 1000)
                    .filter(creature -> creature.getDetails().getPenActivationTime().isBefore(activationTime))
                    .collect(Collectors.toSet());
            //increase maturity
            creatures.forEach(creature ->
                    creature.getDetails().setMaturity(creature.getDetails().getMaturity() + (11 - creature.getGeneration())));
        }
    }

    public void breed(Pen pen, ZonedDateTime activationTime) {
        Set<Creature> breedableCreatures = pen.getCreatures().stream()
                .filter(CreatureUtil::isBreedable)
                .filter(creature -> creature.getDetails().getPenActivationTime().isBefore(activationTime))
                .collect(Collectors.toSet());

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
            breed(males.get(i), females.get(i), activationTime);
        }
    }

    private void breed(Creature male, Creature female, ZonedDateTime activationTime) {
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
        female.getDetails().setPregnancyStartTime(activationTime);
        //female generation has an impact on the pregnancy time
        female.getDetails().setPregnancyEndTime(
                activationTime.plus(creaturesProperties.getPregnancyTimeValue() * female.getGeneration(),
                        creaturesProperties.getPregnancyTimeUnit()));
    }
}
