package hrpg.server.creature.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class,
        uses = ColorMapper.class)
public interface CreatureMapper {

    @Mapping(target = "gene1", source = "gene1.code")
    @Mapping(target = "gene2", source = "gene2.code")
    @Mapping(target = "wild", source = "details.wild")
    @Mapping(target = "pregnant", source = "details.pregnant")
    @Mapping(target = "pregnancyStartTime", source = "details.pregnancyStartTime")
    @Mapping(target = "pregnancyEndTime", source = "details.pregnancyEndTime")
    @Mapping(target = "energy", source = "details.energy")
    @Mapping(target = "love", source = "details.love")
    @Mapping(target = "thirst", source = "details.thirst")
    @Mapping(target = "hunger", source = "details.hunger")
    @Mapping(target = "maturity", source = "details.maturity")
    CreatureDto toDto(Creature entity);

    CreatureCriteria toCriteria(CreatureSearch search);
}
