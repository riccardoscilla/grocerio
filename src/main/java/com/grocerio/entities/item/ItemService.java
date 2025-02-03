package com.grocerio.entities.item;

import com.grocerio.entities.category.CategoryService;
import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.item.model.ItemEdit;
import com.grocerio.entities.item.model.ItemNew;
import com.grocerio.entities.shelf.ShelfService;
import jakarta.persistence.EntityManager;
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
    private final EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryService categoryService, ShelfService shelfService, EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.categoryService = categoryService;
        this.shelfService = shelfService;
        this.entityManager = entityManager;
    }

    public List<Item> getAll(Long shelfId) {
        return itemRepository.findAllByShelfId(shelfId);
    }

    public Item get(Long id, Long shelfId) {
        return itemRepository.findByIdAndShelfId(id, shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Item getOrSave(ItemNew itemNew, Long shelfId) {
        return this.itemRepository.findByNameAndShelfId(itemNew.name, shelfId)
                .map(item -> {
                   ItemEdit itemEdit = ItemEdit.from(item, itemNew);
                   return edit(itemEdit, shelfId);
                })
                .orElseGet(() -> save(itemNew, shelfId));
    }

    public Item save(ItemNew itemNew, Long shelfId) {
        Item item = new Item();
        item.name = itemNew.name.toLowerCase();
        item.category = categoryService.get(itemNew.categoryId, shelfId);
        item.shelf = shelfService.get(shelfId);

        item.favourite = itemNew.favourite;
        item.lastPurchaseDate = itemNew.lastPurchaseDate;

        return itemRepository.save(item);
    }

    public Item edit(ItemEdit itemEdit, Long shelfId) {
        Item item = get(itemEdit.id, shelfId);
        item.name = itemEdit.name;
        item.category = categoryService.get(itemEdit.categoryId, shelfId);
        item.shelf = shelfService.get(shelfId);

        item.favourite = itemEdit.favourite;
        item.lastPurchaseDate = itemEdit.lastPurchaseDate;

        return itemRepository.save(item);
    }

    public void delete(Long id, Long shelfId) {
        String query = "SELECT i FROM Item i WHERE i.id = :id AND i.shelf.id = :shelfId";
        Item item = entityManager
                .createQuery(query, Item.class)
                .setParameter("id", id)
                .setParameter("shelfId", shelfId)
                .getSingleResult();

        entityManager.remove(item);
    }
}
