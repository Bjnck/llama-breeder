package hrpg.server.shop.resource;

import hrpg.server.common.mapper.SpringBaseMapperConfig;
import hrpg.server.shop.service.ShopItemDto;
import hrpg.server.shop.service.ShopItemSearch;
import org.mapstruct.Mapper;

@Mapper(config = SpringBaseMapperConfig.class)
public interface ShopItemResourceMapper {

    ShopItemResponse toResponse(ShopItemDto dto);

    ShopItemSearch toSearch(ShopItemQueryParams params);
}
