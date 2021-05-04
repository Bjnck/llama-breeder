package hrpg.server.user.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.user.dao.User;
import hrpg.server.user.dao.UserDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class)
public interface UserMapper {

    @Mapping(target = "level", source = "details.level")
    @Mapping(target = "coins", source = "details.coins")
    @Mapping(target = "version", source = "details.version")
    UserDto toDto(User entity);

    @Mapping(target = "details", source = "dto")
    User toEntity(UserDto dto);

    UserDetails toDetails(UserDto dto);
}
