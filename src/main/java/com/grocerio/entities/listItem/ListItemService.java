package com.grocerio.entities.listItem;

import com.grocerio.entities.item.ItemService;
import com.grocerio.entities.item.model.ItemEdit;
import com.grocerio.entities.listItem.model.ListItem;
import com.grocerio.entities.listItem.model.ListItemEdit;
import com.grocerio.entities.listItem.model.ListItemNew;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class ListItemService {
    private final ListItemRepository listItemRepository;
    private final ItemService itemService;

    public ListItemService(ListItemRepository listItemRepository, ItemService itemService) {
        this.listItemRepository = listItemRepository;
        this.itemService = itemService;
    }

    public List<ListItem> getAll(Long shelfId) {
        return listItemRepository.findAllByShelfId(shelfId);
    }

    public ListItem get(Long id, Long shelfId) {
        return listItemRepository.findByIdAndShelfId(id, shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ListItem saveOrEdit(ListItemNew listItemNew, Long shelfId) {
        return listItemRepository.findByNameAndShelfId(listItemNew.itemNew.name, shelfId)
                .map(listItem -> {
                    ListItemEdit listItemEdit = ListItemEdit.from(listItem, listItemNew);
                    return edit(listItemEdit, shelfId);
                })
                .orElseGet(() -> save(listItemNew, shelfId));
    }

    public ListItem save(ListItemNew listItemNew, Long shelfId) {
        ListItem listItem = new ListItem();
        listItem.quantity = listItemNew.quantity;
        listItem.insertionDate = listItemNew.insertionDate;
        listItem.note = listItemNew.note;
        listItem.inCart = listItemNew.inCart;
        listItem.item = itemService.saveOrEdit(listItemNew.itemNew, shelfId);

        return listItemRepository.save(listItem);
    }

    public ListItem edit(ListItemEdit listItemEdit, Long shelfId) {
        ListItem listItem = get(listItemEdit.id, shelfId);
        listItem.quantity = listItemEdit.quantity;
        listItem.insertionDate = listItemEdit.insertionDate;
        listItem.note = listItemEdit.note;
        listItem.inCart = listItemEdit.inCart;
        listItem.item = itemService.saveOrEdit(listItemEdit.itemNew, shelfId);

        return listItemRepository.save(listItem);
    }

    public void delete(Long id, Long shelfId) {
        this.listItemRepository.deleteByIdAndShelfId(id, shelfId);
    }

    public List<ListItem> getInCart(Long shelfId) {
        return listItemRepository.findAllCheckedByShelfId(shelfId);
    }

    public void updateLastPurchaseDate(ListItem listItem, Long shelfId) {
        ItemEdit itemEdit = ItemEdit.from(listItem.item);
        itemEdit.lastPurchaseDate = Instant.now();
        itemService.edit(itemEdit, shelfId);
    }

}
