package hrpg.server.creature.service;

import hrpg.server.common.properties.GenesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.GeneRepository;
import hrpg.server.creature.type.Gene;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GeneFactoryImpl implements GeneFactory {

    private final GeneRepository geneRepository;
    private final GenesProperties genesParameters;

    public GeneFactoryImpl(GeneRepository geneRepository,
                           ParametersProperties parametersProperties) {
        this.geneRepository = geneRepository;
        this.genesParameters = parametersProperties.getGenes();
    }

    @Override
    public Optional<hrpg.server.creature.dao.Gene> getForCapture(int netQuality) {
        Gene gene = null;
        if (new Random().nextInt(100) < getChance(netQuality)) {
            //check get normal gene
            int random = new Random().nextInt(100);
            if (random < genesParameters.getChanceLove())
                gene = Gene.LOVE;
            else if (random < genesParameters.getChanceLove() + genesParameters.getChanceHunger())
                gene = Gene.HUNGER;
            else if (random < genesParameters.getChanceLove() + genesParameters.getChanceHunger() + genesParameters.getChanceThirst())
                gene = Gene.THIRST;
            else
                gene = Gene.FERTILE;
        } else if (new Random().nextInt(100) < getSpecialChance(netQuality)) {
            //check get special gene
        }

        if (gene != null)
            return geneRepository.findByCode(gene);
        else
            return Optional.empty();
    }

    private int getChance(int netQuality) {
        if (netQuality == 0)
            return genesParameters.getChanceQuality0();
        else if (netQuality == 2)
            return genesParameters.getChanceQuality2();
        else if (netQuality == 5)
            return genesParameters.getChanceQuality5();
        else
            return genesParameters.getChanceQuality8();
    }

    private int getSpecialChance(int netQuality) {
        if (netQuality == 0)
            return 0;
        else if (netQuality == 2)
            return genesParameters.getSpecialChanceQuality2();
        else if (netQuality == 5)
            return genesParameters.getSpecialChanceQuality5();
        else
            return genesParameters.getSpecialChanceQuality8();
    }

    @Override
    public Pair<Optional<hrpg.server.creature.dao.Gene>, Optional<hrpg.server.creature.dao.Gene>> getForBirth(
            hrpg.server.creature.dao.Gene parent1Gene1, hrpg.server.creature.dao.Gene parent1Gene2,
            hrpg.server.creature.dao.Gene parent2Gene1, hrpg.server.creature.dao.Gene parent2Gene2) {
        List<hrpg.server.creature.dao.Gene> genes = new ArrayList<>();

        if (parent1Gene1 != null && chanceOfGene()) genes.add(parent1Gene1);
        else if (parent1Gene2 != null && chanceOfGene()) genes.add(parent1Gene2);
        if (parent2Gene1 != null && chanceOfGene()) genes.add(parent2Gene1);
        else if (parent2Gene2 != null && chanceOfGene()) genes.add(parent2Gene2);

        genes = genes.stream()
                .sorted(Comparator.comparingLong(hrpg.server.creature.dao.Gene::getId))
                .collect(Collectors.toList());

        return Pair.of(
                genes.size() >= 1 ? Optional.of(genes.get(0)) : Optional.empty(),
                genes.size() >= 2 ? Optional.of(genes.get(1)) : Optional.empty());
    }

    private boolean chanceOfGene() {
        return new Random().nextInt(100) < 50;
    }
}
