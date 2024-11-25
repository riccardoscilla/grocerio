package com.grocerio.entities.category;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.shelf.ShelfService;
import com.grocerio.entities.shelf.model.Shelf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ShelfService shelfService;

    Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ShelfService shelfService) {
        this.categoryRepository = categoryRepository;
        this.shelfService = shelfService;
    }

    public List<Category> getAll(Long shelfId) {
        return categoryRepository.findAllByShelfId(shelfId);
    }

    public Category get(Long categoryId, Long shelfId) {
        return categoryRepository.findByIdAndShelfId(categoryId, shelfId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
