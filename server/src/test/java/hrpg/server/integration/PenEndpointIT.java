package hrpg.server.integration;

import hrpg.server.creature.dao.*;
import hrpg.server.creature.type.Sex;
import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.item.type.ItemCode;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PenEndpointIT extends AbstractIntegrationTest {

    @Autowired
    private PenRepository penRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CreatureRepository creatureRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private GeneRepository geneRepository;

    @Test
    @SneakyThrows
    void pen_endpoints() {
        //create pen
        Creature creature = givenCreature();
        Item item = givenItem();
        Pen pen = givenPen(creature, item);

        //get all pens found 1
        get(PEN_URL)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(1))
                .andExpect(jsonPath("$._embedded.pens[*].id", hasItem(pen.getId().intValue())))
                .andExpect(jsonPath("$._embedded.pens[*].size", hasItem(pen.getSize())))
                .andExpect(jsonPath("$._embedded.pens[*]._links.self.href", hasItem(containsString(PEN_URL + "/" + pen.getId()))));

        //get pen
        get(PEN_URL + "/" + pen.getId())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(pen.getId().intValue())))
                .andExpect(jsonPath("$.size", equalTo(pen.getSize())))
                .andExpect(jsonPath("$.creatures[*].id", hasItem(creature.getId().intValue())))
                .andExpect(jsonPath("$.creatures[*]._links.self.href", hasItem(containsString(CREATURE_URL + "/" + creature.getId()))))
                .andExpect(jsonPath("$.items[*].id", hasItem(item.getId().intValue())))
                .andExpect(jsonPath("$.items[*]._links.self.href", hasItem(containsString(ITEM_URL + "/" + item.getId()))))
                .andExpect(jsonPath("$._links.self.href", containsString(PEN_URL + "/" + pen.getId())));
    }

    @Test
    @SneakyThrows
    void pen_updates() {

        //update size insuficientCoins
        //update size less than
        //update size more than 1 -> check pay enough

        //update items not found
        //update items new
        //update items remove
        //udpate items max size
        //update items conflict

        //idem creatures
    }

    private Pen givenPen(Creature creature, Item item) {
        Pen pen = Pen.builder()
                .creature(creature)
                .item(item)
                .build();
        pen.setUserId(userDto.getId());
        return penRepository.save(pen);
    }

    private Creature givenCreature() {
        Color color = colorRepository.findAllByGeneration(1).stream().findFirst().orElseThrow();
        Gene gene = geneRepository.findByCode(hrpg.server.creature.type.Gene.FERTILE).orElseThrow();

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

    private Item givenItem() {
        Item item = Item.builder()
                .code(ItemCode.THIRST)
                .quality(1)
                .build();
        item.setUserId(userDto.getId());
        return itemRepository.save(item);
    }
}
