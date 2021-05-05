package hrpg.server.capture.service;

import hrpg.server.capture.dao.CaptureRepository;
import hrpg.server.creature.service.CreatureDto;
import hrpg.server.creature.service.CreatureFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class CaptureComputorImpl implements CaptureComputor {

    private final CaptureRepository captureRepository;
    private final CreatureFactory creatureFactory;

    public CaptureComputorImpl(CaptureRepository captureRepository,
                               CreatureFactory creatureFactory) {
        this.captureRepository = captureRepository;
        this.creatureFactory = creatureFactory;
    }

    @Transactional
    @Override
    public void compute() {
        compute(null);
    }

    @Transactional
    @Override
    public void compute(Long id) {
        //find ended capture without creature (can be only one has only one capture at the time)
        captureRepository.findByCreatureIdIsNullAndEndTimeLessThanEqual(LocalDateTime.now()).ifPresent(capture -> {
            if (id == null || id.equals(capture.getId())) {
                //generate new creature and add to to capture
                CreatureDto creatureDto = creatureFactory.generateForCapture(capture.getQuality(), capture.getEndTime().toLocalDate());
                capture.setCreatureId(creatureDto.getId());
            }
        });
    }
}
