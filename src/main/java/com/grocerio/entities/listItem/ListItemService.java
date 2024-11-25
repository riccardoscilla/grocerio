package com.grocerio.entities.listItem;

import com.grocerio.entities.item.ItemService;
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

    public ListItem get(Long itemId, Long shelfId) {
        return listItemRepository.findByIdAndShelfId(itemId, shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void save(ListItemNew listItemNew, Long shelfId) {
        ListItem listItem = new ListItem();
        listItem.quantity = listItemNew.quantity;
        listItem.insertionDate = listItemNew.insertionDate;
        listItem.note = listItemNew.note;
        listItem.item = itemService.get(listItemNew.itemId, shelfId);

        this.listItemRepository.save(listItem);
    }

    public void edit(ListItemEdit listItemEdit, Long shelfId) {
        ListItem listItem = get(listItemEdit.id, shelfId);
        listItem.quantity = listItemEdit.quantity;
        listItem.insertionDate = listItemEdit.insertionDate;
        listItem.note = listItemEdit.note;
        listItem.item = itemService.get(listItemEdit.itemId, shelfId);

        this.listItemRepository.save(listItem);
    }

    public void delete(Long id, Long shelfId) {
        this.listItemRepository.deleteByIdAndShelfId(id, shelfId);
    }

}
