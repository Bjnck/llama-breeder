package hrpg.server.creature.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.service.ColorDto;
import hrpg.server.creature.service.CreatureDto;
import hrpg.server.creature.service.CreatureSearch;
import hrpg.server.creature.type.Gene;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class)
public interface CreatureResourceMapper {

    @Mapping(target = "colors", source = "dto")
    @Mapping(target = "genes", source = "dto")
    CreatureResponse toResponse(CreatureDto dto);

    Colors toColors(CreatureDto dto);

    Color toColor(ColorDto dto);

    Genes toGenes(CreatureDto dto);

    default String toGeneCode(Gene gene) {
        if (gene == null)
            return null;
        return gene.name();
    }

    CreatureSearch toSearch(CreatureQueryParams params);
}
