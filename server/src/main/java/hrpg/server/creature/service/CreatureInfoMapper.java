package hrpg.server.creature.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.dao.CreatureInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class,
        uses = ColorMapper.class)
public interface CreatureInfoMapper {

    @Mapping(target = "gene1", source = "gene1.code")
    @Mapping(target = "gene2", source = "gene2.code")
    CreatureInfoDto toDto(CreatureInfo entity);
}
