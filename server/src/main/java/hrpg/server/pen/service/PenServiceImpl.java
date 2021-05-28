package hrpg.server.pen.service;

import hrpg.server.common.exception.InsufficientCoinsException;
import hrpg.server.common.properties.ParametersProperties;
import hrpg.server.common.properties.PensProperties;
import hrpg.server.creature.dao.Creature;
import hrpg.server.creature.dao.CreatureRepository;
import hrpg.server.creature.service.exception.CreatureInUseException;
import hrpg.server.creature.service.exception.CreatureNotFoundException;
import hrpg.server.creature.service.exception.MaxCreaturesException;
import hrpg.server.item.dao.Item;
import hrpg.server.item.dao.ItemRepository;
import hrpg.server.item.service.exception.ItemInUseException;
import hrpg.server.item.service.exception.ItemNotFoundException;
import hrpg.server.item.service.exception.MaxItemsException;
import hrpg.server.pen.dao.Pen;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.pen.service.exception.InvalidPenSizeException;
import hrpg.server.pen.service.exception.PenNotFoundException;
import hrpg.server.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PenServiceImpl implements PenService {

    private final PenRepository penRepository;
    private final PenMapper penMapper;
    private final CreatureRepository creatureRepository;
    private final ItemRepository itemRepository;
    private final UserService userService;
    private final PensProperties pensProperties;

    public PenServiceImpl(PenRepository penRepository,
                          PenMapper penMapper,
                          CreatureRepository creatureRepository,
                          ItemRepository itemRepository,
                          UserService userService,
                          ParametersProperties parametersProperties) {
        this.penRepository = penRepository;
        this.penMapper = penMapper;
        this.creatureRepository = creatureRepository;
        this.itemRepository = itemRepository;
        this.userService = userService;
        pensProperties = parametersProperties.getPens();
    }

    @Override
    public Optional<PenDto> findById(long id) {
        return penRepository.findById(id).map(penMapper::toDto);
    }

    @Override
    public Page<PenDto> search(@NotNull Pageable pageable) {
        return penRepository.findAll(pageable).map(penMapper::toDto);
    }

    @Transactional(rollbackFor = {
            PenNotFoundException.class,
            InvalidPenSizeException.class,
            InsufficientCoinsException.class,
            ItemInUseException.class,
            MaxItemsException.class,
            ItemNotFoundException.class,
            CreatureNotFoundException.class,
            MaxCreaturesException.class,
            CreatureInUseException.class
    })
    @Override
    public PenDto update(long id, @NotNull PenDto penDto)
            throws PenNotFoundException, InvalidPenSizeException, InsufficientCoinsException, ItemInUseException,
            MaxItemsException, ItemNotFoundException, CreatureNotFoundException, MaxCreaturesException, CreatureInUseException {
        Pen pen = penRepository.findById(id).orElseThrow(PenNotFoundException::new);
        updateSize(pen, penDto.getSize());
        updateItems(pen, penDto.getItemIds());
        updateCreatures(pen, penDto.getCreatureIds());
        return penMapper.toDto(pen);
    }

    private void updateSize(Pen pen, int size) throws InvalidPenSizeException, InsufficientCoinsException {
        //max size is 20
        if (size > 20) size = 20;

        if (size < pen.getSize()) throw new InvalidPenSizeException();
        else if (size > pen.getSize()) {
            int priceForUpdate = 0;
            for (int i = pen.getSize() + 1; i <= size; i++) {
                priceForUpdate += pensProperties.getPrice().get("size-" + i);
            }
            userService.removeCoins(priceForUpdate);
            pen.setSize(size);
        }
    }

    private void updateItems(Pen pen, Set<Long> itemIds)
            throws ItemNotFoundException, ItemInUseException, MaxItemsException {
        if (itemIds == null) itemIds = new HashSet<>();

        if (pen.getSize() < itemIds.size())
            throw new MaxItemsException();

        Set<Item> items = new HashSet<>();
        for (Long itemId : itemIds) {
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
            if (penRepository.existsByItemsContainingAndIdNot(item, pen.getId())) throw new ItemInUseException(itemId);
            items.add(item);
        }
        pen.setItems(items);
    }

    private void updateCreatures(Pen pen, Set<Long> creaturesIds)
            throws MaxCreaturesException, CreatureNotFoundException, CreatureInUseException {
        if (creaturesIds == null) creaturesIds = new HashSet<>();

        if (pen.getSize() < creaturesIds.size())
            throw new MaxCreaturesException();

        Set<Creature> creatures = new HashSet<>();
        for (Long creatureId : creaturesIds) {
            Creature creature = creatureRepository.findById(creatureId)
                    .orElseThrow(() -> new CreatureNotFoundException(creatureId));
            if (penRepository.existsByCreaturesContainingAndIdNot(creature, pen.getId()))
                throw new CreatureInUseException(creatureId);
            creatures.add(creature);
        }
        pen.setCreatures(creatures);
    }
}
