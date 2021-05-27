package hrpg.server.pen.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.pen.service.PenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class)
public interface PenResourceMapper {
    @Mapping(target = "creatureIds", source = "creatures")
    @Mapping(target = "itemIds", source = "items")
    PenDto toRequest(PenRequest request);

    default Long toCreatureId(PenCreature creature) {
        return creature.getId();
    }

    default Long toItemId(PenItem item) {
        return item.getId();
    }

    @Mapping(target = "creatures", source = "creatureIds")
    @Mapping(target = "items", source = "itemIds")
    PenResponse toResponse(PenDto dto);

    default PenCreature toCreature(Long id) {
        return PenCreature.builder().id(id).build();
    }

    default PenItem toItem(Long id) {
        return PenItem.builder().id(id).build();
    }
}
