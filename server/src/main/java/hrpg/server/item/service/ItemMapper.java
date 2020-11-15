package hrpg.server.item.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.item.dao.Item;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface ItemMapper {
    ItemDto toDto(Item entity);

    Item toEntity(ItemDto dto);

    Item toSearch(ItemSearch search);
}
