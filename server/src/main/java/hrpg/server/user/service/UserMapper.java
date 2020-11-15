package hrpg.server.user.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.user.dao.User;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);
}
