package hrpg.server.pen.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.pen.service.PenDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class)
public interface PenResourceMapper {
    @Mapping(target = "creatureIds", source = "creatures")
    @Mapping(target = "itemIds", source = "items")
    PenDto toDto(PenRequest request);

    default Long toCreatureId(PenCreatureRequest creature) {
        return creature.getId();
    }

    default Long toItemId(PenItemRequest item) {
        return item.getId();
    }

    @Mapping(target = "creatures", source = "creatureIds")
    @Mapping(target = "items", source = "itemIds")
    PenResponse toResponse(PenDto dto);

    default PenCreatureResponse toCreature(Long id) {
        return PenCreatureResponse.builder().id(id).build();
    }

    default PenItemResponse toItem(Long id) {
        return PenItemResponse.builder().id(id).build();
    }
}
