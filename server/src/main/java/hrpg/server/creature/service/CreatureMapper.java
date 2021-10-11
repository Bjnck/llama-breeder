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
        uses = {
                CreatureInfoMapper.class,
                ColorMapper.class
        })
public interface CreatureMapper {

    @Mapping(target = "originalUser", source = "originalUserId", qualifiedByName = "toOriginalUser")
    CreatureDto toDto(Creature entity, @Context UserService userService);

    @Named("toOriginalUser")
    default String toOriginalUser(Integer originalUserId, @Context UserService userService) {
        return userService.getNameFromId(originalUserId);
    }

    CreatureCriteria toCriteria(CreatureSearch search);
}
