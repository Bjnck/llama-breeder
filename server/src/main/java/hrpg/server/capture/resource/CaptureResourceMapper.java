package hrpg.server.capture.resource;

import hrpg.server.capture.service.CaptureDto;
import hrpg.server.common.mapper.SpringBaseMapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class)
public interface CaptureResourceMapper {

    @Mapping(target = "bait", source = "baitGeneration")
    CaptureResponse toResponse(CaptureDto dto);
}
