package hrpg.server.creature.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.service.CreatureDto;
import hrpg.server.creature.service.CreatureInfoDto;
import hrpg.server.creature.service.CreatureSearch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class,
        uses = CreatureInfoResourceMapper.class)
public interface CreatureResourceMapper {

    @Mapping(target = "sex", source = "info.sex")
    @Mapping(target = "colors", source = "info")
    @Mapping(target = "genes", source = "info")
    @Mapping(target = "parents", source = "dto")
    @Mapping(target = "statistics", source = "dto")
    @Mapping(target = "pregnancyMale", source = "pregnancyMaleInfo")
    CreatureResponse toResponse(CreatureDto dto);

    Colors toColors(CreatureInfoDto dto);

    Genes toGenes(CreatureInfoDto dto);

    @Mapping(target = "parent1", source = "parentInfo1")
    @Mapping(target = "parent2", source = "parentInfo2")
    Parents toParents(CreatureDto dto);

    @Mapping(target = "colors", source = "dto")
    @Mapping(target = "genes", source = "dto")
    Parent toParent(CreatureInfoDto dto);

    Statistics toStatistics(CreatureDto dto);

    CreatureDto toDto(CreatureRequest request);

    CreatureSearch toSearch(CreatureQueryParams params);
}
