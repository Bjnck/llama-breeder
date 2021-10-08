package hrpg.server.creature.service;

import hrpg.server.creature.dao.Gene;
import org.springframework.data.util.Pair;

import java.util.Optional;

public interface GeneFactory {
    Optional<Gene> getForCapture(int netQuality, Integer baitGeneration);

    Pair<Optional<Gene>, Optional<Gene>> getForBirth(Gene parent1Gene1, Gene parent1Gene2,
                                                     Gene parent2Gene1, Gene parent2Gene2);
}
