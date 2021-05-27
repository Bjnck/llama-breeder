package hrpg.server.capture.service;

import hrpg.server.capture.dao.Bait;
import hrpg.server.capture.dao.BaitRepository;
import hrpg.server.capture.service.exception.BaitUnavailableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaitServiceImpl implements BaitService {

    private final BaitRepository baitRepository;

    public BaitServiceImpl(BaitRepository baitRepository) {
        this.baitRepository = baitRepository;
    }

    @Transactional
    @Override
    public void decreaseCount(int generation) throws BaitUnavailableException {
        Bait bait = baitRepository.findByGeneration(generation)
                .filter(b -> b.getCount() > 0)
                .orElseThrow(BaitUnavailableException::new);
        bait.setCount(bait.getCount() - 1);
    }
}
