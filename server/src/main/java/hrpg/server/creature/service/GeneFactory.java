package hrpg.server.creature.service;

import hrpg.server.creature.dao.Gene;

import java.util.Optional;

public interface GeneFactory {
    Optional<Gene> getForCapture(int nestQuality, Integer baitGeneration);
}
