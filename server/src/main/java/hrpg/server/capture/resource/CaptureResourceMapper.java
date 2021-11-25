package hrpg.server.capture.resource;

import hrpg.server.capture.service.CaptureDto;
import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.creature.resource.CreatureInfoResourceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = SpringBaseMapperConfig.class,
        uses = CreatureInfoResourceMapper.class)
public interface CaptureResourceMapper {

    @Mapping(target = "sex", source = "creatureInfo.sex")
    @Mapping(target = "color", source = "creatureInfo.color1")
    @Mapping(target = "gene", source = "creatureInfo.gene1")
    CaptureResponse toResponse(CaptureDto dto);
}
