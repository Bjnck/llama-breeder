package hrpg.server.integration;

import hrpg.server.creature.dao.*;
import hrpg.server.creature.type.Sex;
import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.item.type.ItemCode;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.pen.resource.PenCreatureRequest;
import hrpg.server.pen.resource.PenItemRequest;
import hrpg.server.pen.resource.PenRequest;
import hrpg.server.user.dao.UserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @Autowired
    private UserRepository userRepository;

    @Test
    @SneakyThrows
    void pen_endpoints() {
        //todo create is unnecessary because always created with user
        //create pen
        Creature creature = givenCreature();
        Item item = givenItem();
        Pen pen = givenPen(creature, item);

        //get all pens found 1
        get(PEN_URL)
                .andExpect(status().isOk())
                .andExpect(jsonPathTotalElements(1))
                .andExpect(jsonPath("$._embedded.pens[*].id", hasItem(pen.getId().intValue())))
                .andExpect(jsonPath("$._embedded.pens[*].size", hasItem(pen.getSize())))
                .andExpect(jsonPath("$._embedded.pens[*]._links.self.href", hasItem(containsString(PEN_URL + "/" + pen.getId()))));

        //get pen
        get(PEN_URL + "/" + pen.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(pen.getId().intValue())))
                .andExpect(jsonPath("$.size", equalTo(pen.getSize())))
                .andExpect(jsonPath("$.creatures[*].id", hasItem(creature.getId().intValue())))
                .andExpect(jsonPath("$.creatures[*]._links.self.href", hasItem(containsString(CREATURE_URL + "/" + creature.getId()))))
                .andExpect(jsonPath("$.items[*].id", hasItem(item.getId().intValue())))
                .andExpect(jsonPath("$.items[*]._links.self.href", hasItem(containsString(ITEM_URL + "/" + item.getId()))))
                .andExpect(jsonPath("$._links.self.href", containsString(PEN_URL + "/" + pen.getId())));

        //todo
        //update pen
    }

    @Test
    @SneakyThrows
    void pen_updates() {
        //todo move most of the test in scenario it
        //create pen
        Creature creature = givenCreature();
        Item item = givenItem();
        Pen pen = givenPen(creature, item);

        //create second pen for conflict
        Creature creatureConflict = givenCreature();
        Item itemConflict = givenItem();
        givenPen(creatureConflict, itemConflict);

        //update items not found
        put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(item.getId() + 100).build())).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("items.id")))
                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")))
                .andExpect(jsonPath("$[*].value", hasItem(Long.toString(item.getId() + 100))));
        //update items remove existing and add new
        Item item2 = givenItem();
        String location = put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(item2.getId()).build())).build())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());
        get(location)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.items[*].id", hasItem(item2.getId().intValue())));
        //update items remove all
        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());
        get(location)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", empty()));
        //update items max size reached
        Item item3 = givenItem();
        Item item4 = givenItem();
        put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).items(
                        new HashSet<>(Arrays.asList(
                                PenItemRequest.builder().id(item.getId()).build(),
                                PenItemRequest.builder().id(item2.getId()).build(),
                                PenItemRequest.builder().id(item3.getId()).build(),
                                PenItemRequest.builder().id(item4.getId()).build())))
                        .build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("items")))
                .andExpect(jsonPath("$[*].code", hasItem("maxSize")));
        //update items conflict
        put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(itemConflict.getId()).build())).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("items.id")))
                .andExpect(jsonPath("$[*].code", hasItem("conflict")))
                .andExpect(jsonPath("$[*].value", hasItem(itemConflict.getId().toString())));

        //update creatures not found
        put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creature.getId() + 100).build())).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("creatures.id")))
                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")))
                .andExpect(jsonPath("$[*].value", hasItem(Long.toString(creature.getId() + 100))));
        //update creatures remove existing and add new
        Creature creature2 = givenCreature();
         location = put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creature2.getId()).build())).build())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());
        get(location)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creatures", hasSize(1)))
                .andExpect(jsonPath("$.creatures[*].id", hasItem(creature2.getId().intValue())));
        //update creatures remove all
        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());
        get(location)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.creatures", empty()));
        //update creatures max size reached
        Creature creature3 = givenCreature();
        Creature creature4 = givenCreature();
        put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).creatures(
                        new HashSet<>(Arrays.asList(
                                PenCreatureRequest.builder().id(creature.getId()).build(),
                                PenCreatureRequest.builder().id(creature2.getId()).build(),
                                PenCreatureRequest.builder().id(creature3.getId()).build(),
                                PenCreatureRequest.builder().id(creature4.getId()).build())))
                        .build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("creatures")))
                .andExpect(jsonPath("$[*].code", hasItem("maxSize")));
        //update creatures conflict
        put(PEN_URL + "/" + pen.getId(),
                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creatureConflict.getId()).build())).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("creatures.id")))
                .andExpect(jsonPath("$[*].code", hasItem("conflict")))
                .andExpect(jsonPath("$[*].value", hasItem(creatureConflict.getId().toString())));

        //update size with insufficient coins
        put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(20).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("size")))
                .andExpect(jsonPath("$[*].code", hasItem("insufficientCoins")));
        //update size more than 1 and check that user has paid
        int coinsBefore = userDto.getCoins();
        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(5).build())
                .andExpect(status().isOk())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
        assertThat(location, notNullValue());
        get(location)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size", equalTo(5)));
        assertTrue(coinsBefore > userRepository.findById(userDto.getId()).orElseThrow().getDetails().getCoins());
        //update size smaller than existing
        put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[*].field", hasItem("size")))
                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")));
    }

    private Pen givenPen(Creature creature, Item item) {
        Pen pen = Pen.builder()
                .size(3)
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
