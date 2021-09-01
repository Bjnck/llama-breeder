//package hrpg.server.integration.scenario;
//
//import hrpg.server.creature.dao.Creature;
//import hrpg.server.integration.AbstractIntegrationTest;
//import hrpg.server.item.dao.Item;
//import hrpg.server.pen.dao.Pen;
//import hrpg.server.pen.resource.PenCreatureRequest;
//import hrpg.server.pen.resource.PenItemRequest;
//import hrpg.server.pen.resource.PenRequest;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpHeaders;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.empty;
//import static org.hamcrest.Matchers.hasSize;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//class PenScenarioIT extends AbstractIntegrationTest {
//
//    @Test
//    @SneakyThrows
//    void size_scenario() {
//        //todo
//        //update size with insufficient coins
//        put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(20).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("size")))
//                .andExpect(jsonPath("$[*].code", hasItem("insufficientCoins")));
//        //update size more than 1 and check that user has paid
//        int coinsBefore = userDto.getCoins();
//        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(5).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size", equalTo(5)));
//        assertTrue(coinsBefore > userRepository.findById(userDto.getId()).orElseThrow().getDetails().getCoins());
//        //update size smaller than existing
//        put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("size")))
//                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")));
//    }
//
//    @Test
//    @SneakyThrows
//    void creatures_scenario() {
//        //todo
//        //update creatures not found
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creature.getId() + 100).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("creatures.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")))
//                .andExpect(jsonPath("$[*].value", hasItem(Long.toString(creature.getId() + 100))));
//        //update creatures remove existing and add new
//        Creature creature2 = givenCreature();
//        location = put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creature2.getId()).build())).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.creatures", hasSize(1)))
//                .andExpect(jsonPath("$.creatures[*].id", hasItem(creature2.getId().intValue())));
//        //update creatures remove all
//        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.creatures", empty()));
//        //update creatures max size reached
//        Creature creature3 = givenCreature();
//        Creature creature4 = givenCreature();
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(
//                        new HashSet<>(Arrays.asList(
//                                PenCreatureRequest.builder().id(creature.getId()).build(),
//                                PenCreatureRequest.builder().id(creature2.getId()).build(),
//                                PenCreatureRequest.builder().id(creature3.getId()).build(),
//                                PenCreatureRequest.builder().id(creature4.getId()).build())))
//                        .build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("creatures")))
//                .andExpect(jsonPath("$[*].code", hasItem("maxSize")));
//        //update creatures conflict
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creatureConflict.getId()).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("creatures.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("conflict")))
//                .andExpect(jsonPath("$[*].value", hasItem(creatureConflict.getId().toString())));
//    }
//
//    @Test
//    @SneakyThrows
//    void items_scenario() {
//        //todo
//        //update items not found
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(item.getId() + 100).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("items.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")))
//                .andExpect(jsonPath("$[*].value", hasItem(Long.toString(item.getId() + 100))));
//        //update items remove existing and add new
//        Item item2 = givenItem();
//        String location = put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(item2.getId()).build())).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.items", hasSize(1)))
//                .andExpect(jsonPath("$.items[*].id", hasItem(item2.getId().intValue())));
//        //update items remove all
//        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.items", empty()));
//        //update items max size reached
//        Item item3 = givenItem();
//        Item item4 = givenItem();
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(
//                        new HashSet<>(Arrays.asList(
//                                PenItemRequest.builder().id(item.getId()).build(),
//                                PenItemRequest.builder().id(item2.getId()).build(),
//                                PenItemRequest.builder().id(item3.getId()).build(),
//                                PenItemRequest.builder().id(item4.getId()).build())))
//                        .build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("items")))
//                .andExpect(jsonPath("$[*].code", hasItem("maxSize")));
//        //update items conflict
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(itemConflict.getId()).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("items.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("conflict")))
//                .andExpect(jsonPath("$[*].value", hasItem(itemConflict.getId().toString())));
//    }
//
//    //todo use item scenario
//
//    //todo automatic item run scenario
//
//
//    @Test
//    @SneakyThrows
//    void pen_updates() {
//        //create pen
//        Creature creature = givenCreature();
//        Item item = givenItem();
//        Pen pen = givenPen(creature, item);
//
//        //create second pen for conflict
//        Creature creatureConflict = givenCreature();
//        Item itemConflict = givenItem();
//        givenPen(creatureConflict, itemConflict);
//
//        //update items not found
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(item.getId() + 100).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("items.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")))
//                .andExpect(jsonPath("$[*].value", hasItem(Long.toString(item.getId() + 100))));
//        //update items remove existing and add new
//        Item item2 = givenItem();
//        String location = put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(item2.getId()).build())).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.items", hasSize(1)))
//                .andExpect(jsonPath("$.items[*].id", hasItem(item2.getId().intValue())));
//        //update items remove all
//        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.items", empty()));
//        //update items max size reached
//        Item item3 = givenItem();
//        Item item4 = givenItem();
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(
//                        new HashSet<>(Arrays.asList(
//                                PenItemRequest.builder().id(item.getId()).build(),
//                                PenItemRequest.builder().id(item2.getId()).build(),
//                                PenItemRequest.builder().id(item3.getId()).build(),
//                                PenItemRequest.builder().id(item4.getId()).build())))
//                        .build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("items")))
//                .andExpect(jsonPath("$[*].code", hasItem("maxSize")));
//        //update items conflict
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).items(Collections.singleton(PenItemRequest.builder().id(itemConflict.getId()).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("items.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("conflict")))
//                .andExpect(jsonPath("$[*].value", hasItem(itemConflict.getId().toString())));
//
//        //update creatures not found
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creature.getId() + 100).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("creatures.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")))
//                .andExpect(jsonPath("$[*].value", hasItem(Long.toString(creature.getId() + 100))));
//        //update creatures remove existing and add new
//        Creature creature2 = givenCreature();
//        location = put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creature2.getId()).build())).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.creatures", hasSize(1)))
//                .andExpect(jsonPath("$.creatures[*].id", hasItem(creature2.getId().intValue())));
//        //update creatures remove all
//        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.creatures", empty()));
//        //update creatures max size reached
//        Creature creature3 = givenCreature();
//        Creature creature4 = givenCreature();
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(
//                        new HashSet<>(Arrays.asList(
//                                PenCreatureRequest.builder().id(creature.getId()).build(),
//                                PenCreatureRequest.builder().id(creature2.getId()).build(),
//                                PenCreatureRequest.builder().id(creature3.getId()).build(),
//                                PenCreatureRequest.builder().id(creature4.getId()).build())))
//                        .build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("creatures")))
//                .andExpect(jsonPath("$[*].code", hasItem("maxSize")));
//        //update creatures conflict
//        put(PEN_URL + "/" + pen.getId(),
//                PenRequest.builder().size(3).creatures(Collections.singleton(PenCreatureRequest.builder().id(creatureConflict.getId()).build())).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("creatures.id")))
//                .andExpect(jsonPath("$[*].code", hasItem("conflict")))
//                .andExpect(jsonPath("$[*].value", hasItem(creatureConflict.getId().toString())));
//
//        //update size with insufficient coins
//        put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(20).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("size")))
//                .andExpect(jsonPath("$[*].code", hasItem("insufficientCoins")));
//        //update size more than 1 and check that user has paid
//        int coinsBefore = userDto.getCoins();
//        location = put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(5).build())
//                .andExpect(status().isOk())
//                .andExpect(header().exists(HttpHeaders.LOCATION))
//                .andReturn().getResponse().getHeader(HttpHeaders.LOCATION);
//        assertThat(location, notNullValue());
//        get(location)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size", equalTo(5)));
//        assertTrue(coinsBefore > userRepository.findById(userDto.getId()).orElseThrow().getDetails().getCoins());
//        //update size smaller than existing
//        put(PEN_URL + "/" + pen.getId(), PenRequest.builder().size(3).build())
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[*].field", hasItem("size")))
//                .andExpect(jsonPath("$[*].code", hasItem("invalidValue")));
//    }
//}
