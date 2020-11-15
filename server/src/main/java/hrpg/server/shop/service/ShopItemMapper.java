package hrpg.server.shop.service;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.shop.dao.ShopItem;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface ShopItemMapper {

    ShopItemDto toDto(ShopItem entity);

    ShopItem toEntity(ShopItemDto dto);
}
