package com.grocerio.entities.category;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.category.model.CategoryEdit;
import com.grocerio.entities.category.model.CategoryNew;
import com.grocerio.entities.item.model.Item;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ShelfService shelfService;
    private final EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ShelfService shelfService, EntityManager entityManager) {
        this.categoryRepository = categoryRepository;
        this.shelfService = shelfService;
        this.entityManager = entityManager;
    }

    public List<Category> getAll(Long shelfId) {
        return categoryRepository.findAllByShelfId(shelfId);
    }

    public Category get(Long categoryId, Long shelfId) {
        return categoryRepository.findByIdAndShelfId(categoryId, shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Category save(CategoryNew categoryNew, Long shelfId) {
        Category category = new Category();
        category.name = categoryNew.name.toLowerCase();
        category.icon = categoryNew.icon;
        category.shelf = shelfService.get(shelfId);

        return this.categoryRepository.save(category);
    }

    public Category edit(CategoryEdit categoryEdit, Long shelfId) {
        Category category = get(categoryEdit.id, shelfId);
        category.name = categoryEdit.name;
        category.icon = categoryEdit.icon;
        category.shelf = shelfService.get(shelfId);

        return this.categoryRepository.save(category);
    }

    public void delete(Long id, Long shelfId) {
        String query = "SELECT c FROM Category c WHERE c.id = :id AND c.shelf.id = :shelfId";
        Category category = entityManager
                .createQuery(query, Category.class)
                .setParameter("id", id)
                .setParameter("shelfId", shelfId)
                .getSingleResult();

        entityManager.remove(category);
    }

}
