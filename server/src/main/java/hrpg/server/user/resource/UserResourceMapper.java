package hrpg.server.user.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.user.service.UserDto;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface UserResourceMapper {

    UserResponse toResponse(UserDto dto);
}
