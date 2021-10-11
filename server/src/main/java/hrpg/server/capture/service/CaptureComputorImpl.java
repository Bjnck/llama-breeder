package hrpg.server.capture.service;

import hrpg.server.capture.dao.CaptureRepository;
import hrpg.server.creature.dao.*;
import hrpg.server.creature.service.CreatureDto;
import hrpg.server.creature.service.CreatureFactory;
import hrpg.server.creature.service.CreatureService;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Component
public class CaptureComputorImpl implements CaptureComputor {

    private final CaptureRepository captureRepository;
    private CreatureRepository creatureRepository;
    private final CreatureFactory creatureFactory;
    private final UserRepository userRepository;
    private final CreatureService creatureService;

    public CaptureComputorImpl(CaptureRepository captureRepository,
                               CreatureRepository creatureRepository,
                               CreatureFactory creatureFactory,
                               UserRepository userRepository,
                               CreatureService creatureService) {
        this.captureRepository = captureRepository;
        this.creatureRepository = creatureRepository;
        this.creatureFactory = creatureFactory;
        this.userRepository = userRepository;
        this.creatureService = creatureService;
    }

    @Transactional
    @Override
    public void compute() {
        compute(null);
    }

    @Transactional
    @Override
    public void compute(Long id) {
        //find ended capture without creature (can get more than one result if capture created at the same time as another ended)
        captureRepository.findAllByCreatureInfoIsNullAndEndTimeLessThanEqual(ZonedDateTime.now()).forEach(capture -> {
            if (id == null || id.equals(capture.getId())) {
                User user = userRepository.get();

                //generate new creature and add info to capture
                try {
                    CreatureDto creatureDto = creatureFactory.generateForCapture(
                            user.getDetails().getLevel(), capture.getQuality(), capture.getBaitGeneration(), capture.getEndTime().toLocalDate());
                    Creature creature = creatureRepository.findById(creatureDto.getId()).orElseThrow();
                    capture.setCreatureInfo(creature.getInfo().toBuilder().id(null).build());
                } catch (MaxCreaturesException e) {
                    //todo notify user
                }

                //end tutorial if second creature captured
                if (user.getDetails().getLevel() == 0 && creatureService.count() >= 2)
                    user.getDetails().setLevel(1);
            }
        });
    }
}
