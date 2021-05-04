package hrpg.server.item.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.item.service.ItemDto;
import hrpg.server.item.service.ItemSearch;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface ItemResourceMapper {

    ItemDto toRequest(ItemRequest request);

    ItemResponse toResponse(ItemDto dto);

    ItemSearch toSearch(ItemQueryParams params);
}
