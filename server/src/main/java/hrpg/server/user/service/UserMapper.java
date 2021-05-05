package hrpg.server.user.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.user.dao.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "level", source = "details.level")
    @Mapping(target = "coins", source = "details.coins")
    UserDto toDto(User entity);
}
