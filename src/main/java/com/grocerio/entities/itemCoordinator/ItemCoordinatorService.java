package com.grocerio.entities.itemCoordinator;

import com.grocerio.entities.listItem.ListItemService;
import com.grocerio.entities.listItem.model.ListItem;
import com.grocerio.entities.listItem.model.ListItemNew;
import com.grocerio.entities.shelfItem.ShelfItemService;
import com.grocerio.entities.shelfItem.model.ShelfItem;
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

    public void deleteAndSaveInList(Long shelfItemId, Long shelfId) {
        ShelfItem shelfItem = shelfItemService.getById(shelfItemId, shelfId);

        shelfItemService.delete(shelfItemId, shelfId);
        listItemService.saveOrEdit(ListItemNew.from(shelfItem), shelfId);
    }

    public void deleteAndSaveInShelf(Long shelfId) {
        List<ListItem> listItemsInCart = listItemService.getInCart(shelfId);

        listItemsInCart.forEach(listItem -> listItemService.updateLastPurchaseDate(listItem, shelfId));
        listItemsInCart.forEach(listItem -> listItemService.delete(listItem.id, shelfId));
        listItemsInCart.stream()
                .map(ShelfItemNew::from)
                .forEach(shelfItemNew -> shelfItemService.saveOrEdit(shelfItemNew, shelfId));
    }
}
