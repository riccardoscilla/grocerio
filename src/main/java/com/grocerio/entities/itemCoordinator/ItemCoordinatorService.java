package com.grocerio.entities.itemCoordinator;

import com.grocerio.entities.listItem.ListItemService;
import com.grocerio.entities.listItem.model.ListItemNew;
import com.grocerio.entities.shelfItem.ShelfItemService;
import com.grocerio.entities.shelfItem.model.ShelfItemNew;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ItemCoordinatorService {
    private final ShelfItemService shelfItemService;
    private final ListItemService listItemService;


    public ItemCoordinatorService(ShelfItemService shelfItemService, ListItemService listItemService) {
        this.shelfItemService = shelfItemService;
        this.listItemService = listItemService;
    }

    public void deleteAndSaveInList(Long shelfItemId, ListItemNew listItemNew, Long shelfId) {
        shelfItemService.delete(shelfItemId, shelfId);
        listItemService.saveOrEdit(listItemNew, shelfId);
    }

    public void deleteAndSaveInShelf(List<Long> listItemIds, List<ShelfItemNew> shelfItemsNew, Long shelfId) {
        listItemIds.forEach(id -> listItemService.delete(id, shelfId));
        shelfItemsNew.forEach(shelfItemNew -> shelfItemService.saveOrEdit(shelfItemNew, shelfId));
    }
}
