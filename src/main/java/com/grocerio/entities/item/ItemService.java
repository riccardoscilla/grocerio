package com.grocerio.entities.item;

import com.grocerio.entities.category.CategoryService;
import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.item.model.ItemEdit;
import com.grocerio.entities.item.model.ItemNew;
import com.grocerio.entities.shelf.ShelfService;
import com.grocerio.entities.shelf.model.Shelf;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryService categoryService;
    private final ShelfService shelfService;

    Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryService categoryService, ShelfService shelfService) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
        this.shelfService = shelfService;
    }

    public List<Item> getAll(Long shelfId) {
        return itemRepository.findAllByShelfId(shelfId);
    }

    public Item get(Long id, Long shelfId) {
        return itemRepository.findByIdAndShelfId(id, shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void save(ItemNew itemNew, Long shelfId) {
        Item item = new Item();
        item.name = itemNew.name;
        item.category = categoryService.get(itemNew.categoryId, shelfId);
        item.shelf = shelfService.get(shelfId);

        this.itemRepository.save(item);
    }

    public void edit(ItemEdit itemEdit, Long shelfId) {
        Item item = get(itemEdit.id, shelfId);
        item.name = itemEdit.name;
        item.category = categoryService.get(itemEdit.categoryId, shelfId);
        item.shelf = shelfService.get(shelfId);

        this.itemRepository.save(item);
    }

    public void delete(Long id, Long shelfId) {
        this.itemRepository.deleteByIdAndShelfId(id, shelfId);
    }
}
