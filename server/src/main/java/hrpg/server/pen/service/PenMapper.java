package hrpg.server.pen.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.dao.Creature;
import hrpg.server.item.dao.Item;
import hrpg.server.pen.dao.Pen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class)
public interface PenMapper {
    @Mapping(target = "itemIds", source = "items")
    @Mapping(target = "creatureIds", source = "creatures")
    PenDto toDto(Pen entity);

    default Long toItemId(Item item) {
        return item.getId();
    }

    default Long toCreatureId(Creature creature) {
        return creature.getId();
    }
}
