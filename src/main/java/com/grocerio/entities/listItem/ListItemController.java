package com.grocerio.entities.listItem;

import com.grocerio.entities.itemCoordinator.ItemCoordinatorService;
import com.grocerio.entities.listItem.model.*;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/list-items")
public class ListItemController {
    private final ListItemService listItemService;
    private final ItemCoordinatorService itemCoordinatorService;
    private final UserService userService;

    @Autowired
    public ListItemController(ListItemService listItemService, ItemCoordinatorService itemCoordinatorService, UserService userService) {
        this.listItemService = listItemService;
        this.itemCoordinatorService = itemCoordinatorService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ListItemVm>> getAll() {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        List<ListItemVm> listItemVms = listItemService.getAll(shelf.id)
                .stream()
                .map(ListItem::toVm)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listItemVms);
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestBody @Valid ListItemNew listItemNew
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        listItemService.saveOrEdit(listItemNew, shelf.id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{listItemId}")
    public ResponseEntity<Void> edit(
            @RequestBody @Valid ListItemEdit listItemEdit
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        listItemService.edit(listItemEdit, shelf.id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{listItemId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long listItemId
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        listItemService.delete(listItemId, shelf.id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete-and-save-in-shelf")
    public ResponseEntity<Void> deleteAndSaveInShelf(
            @RequestBody @Valid ListItemDeleteAndSaveInShelf listItemDeleteAndSaveInShelf
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        itemCoordinatorService.deleteAndSaveInShelf(
                listItemDeleteAndSaveInShelf.listItemIds,
                listItemDeleteAndSaveInShelf.shelfItems,
                shelf.id
        );
        return ResponseEntity.ok().build();
    }
}
