package hrpg.server.capture.resource;

import hrpg.server.capture.service.CaptureDto;
import hrpg.server.common.mapper.SpringBaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface CaptureResourceMapper {

    CaptureResponse toResponse(CaptureDto dto);
}
