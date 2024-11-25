package com.grocerio.entities.shelfItem;

import com.grocerio.entities.itemCoordinator.ItemCoordinatorService;
import com.grocerio.entities.listItem.model.ListItemNew;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.shelfItem.model.ShelfItem;
import com.grocerio.entities.shelfItem.model.ShelfItemEdit;
import com.grocerio.entities.shelfItem.model.ShelfItemNew;
import com.grocerio.entities.shelfItem.model.ShelfItemVm;
import com.grocerio.entities.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shelf-items")
public class ShelfItemController {
    private final ShelfItemService shelfItemService;
    private final ItemCoordinatorService itemCoordinatorService;
    private final UserService userService;

    @Autowired
    public ShelfItemController(ShelfItemService shelfItemService, ItemCoordinatorService itemCoordinatorService, UserService userService) {
        this.shelfItemService = shelfItemService;
        this.itemCoordinatorService = itemCoordinatorService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<ShelfItemVm>> getAll() {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        List<ShelfItemVm> shelfItemVms = shelfItemService.getAll(shelf.id)
                .stream()
                .map(ShelfItem::toVm)
                .collect(Collectors.toList());
        return ResponseEntity.ok(shelfItemVms);
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @RequestBody @Valid ShelfItemNew shelfItemNew
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        shelfItemService.save(shelfItemNew, shelf.id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{shelfItemId}")
    public ResponseEntity<Void> edit(
            @RequestBody @Valid ShelfItemEdit shelfItemEdit
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        shelfItemService.edit(shelfItemEdit, shelf.id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{shelfItemId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long shelfItemId
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        shelfItemService.delete(shelfItemId, shelf.id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{shelfItemId}/deleteAndSaveInList")
    public ResponseEntity<Void> deleteAndSaveInList(
            @PathVariable Long shelfItemId,
            @RequestBody @Valid ListItemNew listItemNew
    ) {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        itemCoordinatorService.deleteAndSaveInList(shelfItemId, listItemNew, shelf.id);
        return ResponseEntity.ok().build();
    }

}
