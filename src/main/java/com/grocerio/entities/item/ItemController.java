package com.grocerio.entities.item;

import com.grocerio.entities.item.model.Item;
import com.grocerio.entities.item.model.ItemEdit;
import com.grocerio.entities.item.model.ItemNew;
import com.grocerio.entities.item.model.ItemVm;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Set<ItemVm>> getAll() {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        Set<ItemVm> itemVms = itemService.getAll(shelf.id).stream()
                .map(Item::toVm)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(itemVms);
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestBody @Valid ItemNew itemNew
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        itemService.save(itemNew, shelf.id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Void> edit(
            @RequestBody @Valid ItemEdit itemEdit
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        itemService.edit(itemEdit, shelf.id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long itemId
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        itemService.delete(itemId, shelf.id);
        return ResponseEntity.ok().build();
    }

}
