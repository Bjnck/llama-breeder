package hrpg.server.collection.service;

import hrpg.server.creature.dao.Color;
import hrpg.server.creature.type.Gene;

public interface CollectionService {
    void registerCollection(Color color, Gene gene1, Gene gene2);
}
