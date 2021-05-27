package hrpg.server.creature.service;

import hrpg.server.common.properties.GenesProperties;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.creature.dao.GeneRepository;
import hrpg.server.creature.type.Gene;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Random;

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
    public Optional<hrpg.server.creature.dao.Gene> getForCapture(int nestQuality, Integer baitGeneration) {
        //todo use bait for calcul
        if (nestQuality == 0)
            return Optional.empty();

        Gene gene = null;
        if (new Random().nextInt(100) < getChance(nestQuality)) {
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
        } else if (new Random().nextInt(100) < getSpecialChance(nestQuality)) {
            //check get special gene
        }

        return Optional.ofNullable(gene).map(geneRepository::findByCode).orElseThrow();
    }

    private int getChance(int nestQuality) {
        if (nestQuality == 1)
            return genesParameters.getChanceQuality1();
        else if (nestQuality == 2)
            return genesParameters.getChanceQuality2();
        else
            return genesParameters.getChanceQuality3();
    }

    private int getSpecialChance(int nestQuality) {
        if (nestQuality == 1)
            return genesParameters.getSpecialChanceQuality1();
        else if (nestQuality == 2)
            return genesParameters.getSpecialChanceQuality2();
        else
            return genesParameters.getSpecialChanceQuality3();
    }
}
