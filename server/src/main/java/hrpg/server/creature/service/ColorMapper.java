package hrpg.server.creature.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.dao.Color;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface ColorMapper {

    ColorDto toDto(Color entity);
}
