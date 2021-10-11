package hrpg.server.creature.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.service.ColorDto;
import hrpg.server.creature.type.Gene;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface CreatureInfoResourceMapper {

    Color toResource(ColorDto dto);

    default String toGeneCode(Gene gene) {
        if (gene == null)
            return null;
        return gene.name();
    }
}
