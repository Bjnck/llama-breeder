package hrpg.server.item.service;

import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository,
                           ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        return itemMapper.toDto(itemRepository.save(itemMapper.toEntity(itemDto)));
    }

    @Override
    public Optional<ItemDto> findById(String id) {
        return itemRepository.findById(id).map(itemMapper::toDto);
    }

    @Override
    public Page<ItemDto> search(ItemSearch search, Pageable pageable) {
        return itemRepository.findAllWithUserId(Example.of(
                Optional.ofNullable(search).map(itemMapper::toSearch).orElse(new Item())), pageable)
                .map(itemMapper::toDto);
    }

    @Override
    public long count(ItemSearch search) {
        return itemRepository.countWithUserId(Example.of(
                Optional.ofNullable(search).map(itemMapper::toSearch).orElse(new Item())));
    }

    @Override
    public void delete(String id) {
        itemRepository.findById(id).ifPresent(itemRepository::delete);
    }
}
