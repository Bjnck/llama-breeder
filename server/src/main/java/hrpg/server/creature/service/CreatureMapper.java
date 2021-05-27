package hrpg.server.creature.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureCriteria;
import hrpg.server.user.service.UserService;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
    @Mapping(target = "originalUser", source = "originalUserId", qualifiedByName = "toOriginalUser")
    CreatureDto toDto(Creature entity, @Context UserService userService);

    @Named("toOriginalUser")
    default String toOriginalUser(Integer originalUserId, @Context UserService userService) {
        return userService.getNameFromId(originalUserId);
    }

    CreatureCriteria toCriteria(CreatureSearch search);
}
