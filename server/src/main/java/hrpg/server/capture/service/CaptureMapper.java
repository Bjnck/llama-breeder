package hrpg.server.capture.service;

import hrpg.server.capture.dao.Capture;
import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.service.CreatureInfoMapper;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class,
        uses = CreatureInfoMapper.class)
public interface CaptureMapper {

    CaptureDto toDto(Capture entity);
}
