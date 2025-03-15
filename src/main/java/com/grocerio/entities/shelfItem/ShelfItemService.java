package com.grocerio.entities.shelfItem;

import com.grocerio.entities.item.ItemService;
import com.grocerio.entities.shelfItem.model.ShelfItem;
import com.grocerio.entities.shelfItem.model.ShelfItemEdit;
import com.grocerio.entities.shelfItem.model.ShelfItemNew;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ShelfItemService {
    private final ShelfItemRepository shelfItemRepository;
    private final ItemService itemService;

    public ShelfItemService(ShelfItemRepository shelfItemRepository, ItemService itemService) {
        this.shelfItemRepository = shelfItemRepository;
        this.itemService = itemService;
    }

    public List<ShelfItem> getAll(Long shelfId) {
        return shelfItemRepository.findAllByShelfId(shelfId);
    }

    public ShelfItem getById(Long id, Long shelfId) {
        return shelfItemRepository.findByIdAndShelfId(id, shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ShelfItem saveOrEdit(ShelfItemNew shelfItemNew, Long shelfId) {
        return shelfItemRepository.findByNameAndShelfId(shelfItemNew.itemNew.name, shelfId)
                .map(shelfItem -> {
                    ShelfItemEdit shelfItemEdit = ShelfItemEdit.from(shelfItem, shelfItemNew);
                    return edit(shelfItemEdit, shelfId);
                })
                .orElseGet(() -> save(shelfItemNew, shelfId));
    }

    public ShelfItem save(ShelfItemNew shelfItemNew, Long shelfId) {
        ShelfItem shelfItem = new ShelfItem();
        shelfItem.quantity = shelfItemNew.quantity;
        shelfItem.purchaseDate = shelfItemNew.purchaseDate;
        shelfItem.note = shelfItemNew.note;
        shelfItem.item = itemService.saveOrEdit(shelfItemNew.itemNew, shelfId);

        return this.shelfItemRepository.save(shelfItem);
    }

    public ShelfItem edit(ShelfItemEdit shelfItemEdit, Long shelfId) {
        ShelfItem shelfItem = getById(shelfItemEdit.id, shelfId);
        shelfItem.quantity = shelfItemEdit.quantity;
        shelfItem.purchaseDate = shelfItemEdit.purchaseDate;
        shelfItem.note = shelfItemEdit.note;
        shelfItem.item = itemService.saveOrEdit(shelfItemEdit.itemNew, shelfId);

        return this.shelfItemRepository.save(shelfItem);
    }

    public void delete(Long id, Long shelfId) {
        this.shelfItemRepository.deleteByIdAndShelfId(id, shelfId);
    }
}
