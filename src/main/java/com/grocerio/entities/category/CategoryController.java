package com.grocerio.entities.category;

import com.grocerio.entities.category.model.Category;
import com.grocerio.entities.category.model.CategoryEdit;
import com.grocerio.entities.category.model.CategoryNew;
import com.grocerio.entities.category.model.CategoryVm;
import com.grocerio.entities.item.model.ItemEdit;
import com.grocerio.entities.item.model.ItemNew;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestBody @Valid CategoryNew categoryNew
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        categoryService.save(categoryNew, shelf.id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Void> edit(
            @RequestBody @Valid CategoryEdit categoryEdit
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        categoryService.edit(categoryEdit, shelf.id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long categoryId
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        categoryService.delete(categoryId, shelf.id);
        return ResponseEntity.ok().build();
    }

}
