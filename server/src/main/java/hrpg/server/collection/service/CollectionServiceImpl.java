package hrpg.server.collection.service;

import hrpg.server.collection.dao.Collection;
import hrpg.server.collection.dao.CollectionRepository;
import hrpg.server.creature.dao.Color;
import hrpg.server.creature.type.Gene;
import hrpg.server.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionServiceImpl implements CollectionService {
    private static final Integer GLOBAL_POINTS = 10;
    private static final Integer GENE_POINTS = 5;

    private final CollectionRepository collectionRepository;
    private final UserService userService;

    public CollectionServiceImpl(CollectionRepository collectionRepository,
                                 UserService userService) {
        this.collectionRepository = collectionRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public void registerCollection(Color color, Gene gene1, Gene gene2) {
        int points = 0;

        Collection collection = collectionRepository.findOneByColor(color).orElse(null);
        if (collection == null) {
            collection = collectionRepository.save(Collection.builder().color(color).build());
            points += GLOBAL_POINTS;
        }

        if (gene1 != null) {
            points += setGene(collection, gene1);
        }
        if (gene2 != null) {
            points += setGene(collection, gene2);
        }

        userService.addPoints(points);
    }

    private int setGene(Collection collection, Gene gene) {
        int points = 0;
        if (Gene.CHRISTMAS.equals(gene) && !collection.isChristmas()) {
            collection.setChristmas(true);
            points += GENE_POINTS;
        }
        return points;
    }
}
