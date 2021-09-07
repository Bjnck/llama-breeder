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
    public Optional<hrpg.server.creature.dao.Gene> getForCapture(int netQuality, Integer baitGeneration) {
        //todo use bait for calcul
        if (netQuality == 0)
            return Optional.empty();

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
        if (netQuality == 1)
            return genesParameters.getChanceQuality1();
        else if (netQuality == 2)
            return genesParameters.getChanceQuality2();
        else
            return genesParameters.getChanceQuality3();
    }

    private int getSpecialChance(int netQuality) {
        if (netQuality == 1)
            return genesParameters.getSpecialChanceQuality1();
        else if (netQuality == 2)
            return genesParameters.getSpecialChanceQuality2();
        else
            return genesParameters.getSpecialChanceQuality3();
    }
}
