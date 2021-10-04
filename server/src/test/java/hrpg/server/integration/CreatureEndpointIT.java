package hrpg.server.integration;

import hrpg.server.creature.dao.*;
import hrpg.server.creature.type.Sex;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreatureEndpointIT extends AbstractIntegrationTest {

    @Autowired
    private CreatureRepository creatureRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private GeneRepository geneRepository;

    @Test
    @SneakyThrows
    void creature_endpoints() {
        //add creature in db
        Color color = colorRepository.findAllByGeneration(1).stream().findFirst().orElseThrow();
        Gene gene = geneRepository.findByCode(hrpg.server.creature.type.Gene.FERTILE).orElseThrow();
        Creature creature = givenCreature(color, gene);

        //todo check statitisc default values
        //get creature
        get(CREATURE_URL + "/" + creature.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(creature.getId().intValue())))
                .andExpect(jsonPath("$.sex", equalTo(creature.getSex().name())))
                .andExpect(jsonPath("$.generation", equalTo(creature.getGeneration())))
                .andExpect(jsonPath("$.name", equalTo(creature.getName())))
                .andExpect(jsonPath("$.originalUser", equalTo(userDto.getName())))
                .andExpect(jsonPath("$.parentId1").doesNotExist())
                .andExpect(jsonPath("$.parentId2").doesNotExist())
                .andExpect(jsonPath("$.colors.color1.code", equalTo(color.getCode())))
                .andExpect(jsonPath("$.colors.color1.name", equalTo(color.getName())))
                .andExpect(jsonPath("$.colors.color2").doesNotExist())
                .andExpect(jsonPath("$.genes.gene1", equalTo(gene.getCode().name())))
                .andExpect(jsonPath("$.genes.gene2").doesNotExist())
                .andExpect(jsonPath("$._links.self.href", endsWith(CREATURE_URL + "/" + creature.getId())));

        //todo test statistics returned

        //todo delete creature
//        delete(CREATURE_URL + "/" + creature.getId())
//                .andExpect(status().isOk());
    }

    //todo creature search

    private Creature givenCreature(Color color, Gene gene) {
        Creature creature = Creature.builder()
                .originalUserId(userDto.getId())
                .createDate(LocalDate.now())
                .sex(Sex.F)
                .color1(color)
                .gene1(gene)
                .build();
        creature.setUserId(userDto.getId());
        creature.setDetails(CreatureDetails.builder()
                .creature(creature)
                .wild(true)
                .build());
        return creatureRepository.save(creature);
    }
}
