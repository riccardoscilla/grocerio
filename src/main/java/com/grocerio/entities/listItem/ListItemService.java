package com.grocerio.entities.listItem;

import com.grocerio.entities.item.ItemService;
import com.grocerio.entities.item.model.ItemEdit;
import com.grocerio.entities.item.model.ItemNew;
import com.grocerio.entities.listItem.model.ListItem;
import com.grocerio.entities.listItem.model.ListItemEdit;
import com.grocerio.entities.listItem.model.ListItemNew;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        return listItemRepository.findByNameAndShelfId(listItemNew.itemName, shelfId)
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

        ItemNew itemNew = new ItemNew(listItemNew.itemName, listItemNew.categoryId);
        listItem.item = itemService.getOrSave(itemNew, shelfId);

        return this.listItemRepository.save(listItem);
    }

    public ListItem edit(ListItemEdit listItemEdit, Long shelfId) {
        ListItem listItem = get(listItemEdit.id, shelfId);
        listItem.quantity = listItemEdit.quantity;
        listItem.insertionDate = listItemEdit.insertionDate;
        listItem.note = listItemEdit.note;

        ItemNew itemNew = new ItemNew(listItemEdit.itemName, listItemEdit.categoryId);
        listItem.item = itemService.getOrSave(itemNew, shelfId);

        return this.listItemRepository.save(listItem);
    }

    public void delete(Long id, Long shelfId) {
        this.listItemRepository.deleteByIdAndShelfId(id, shelfId);
    }

}
