package hrpg.server.item.service;

import hrpg.server.item.dao.ItemRepository;
import hrpg.server.pen.dao.PenRepository;
import hrpg.server.pen.service.PenComputor;
import org.springframework.stereotype.Component;

@Component
public class ItemComputorImpl implements ItemComputor {
    private final PenRepository penRepository;
    private final PenComputor penComputor;
    private final ItemRepository itemRepository;

    public ItemComputorImpl(PenRepository penRepository,
                            PenComputor penComputor,
                            ItemRepository itemRepository) {
        this.penRepository = penRepository;
        this.penComputor = penComputor;
        this.itemRepository = itemRepository;
    }

    @Override
    public void compute() {
        //update life for all items in pen
        penComputor.compute();
    }

    @Override
    public void compute(long id) {
        //update life for item if in pen
        itemRepository.findById(id)
                .flatMap(penRepository::findByItemsContaining)
                .ifPresent(pen -> penComputor.compute(pen.getId()));
    }
}
