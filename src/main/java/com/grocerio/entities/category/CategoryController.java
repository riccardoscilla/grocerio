package com.grocerio.entities.category;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.category.model.CategoryVm;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<CategoryVm>> getAll() {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        List<CategoryVm> categoryVms = categoryService.getAll(shelf.id)
                .stream()
                .map(Category::toVm)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryVms);
    }

}
