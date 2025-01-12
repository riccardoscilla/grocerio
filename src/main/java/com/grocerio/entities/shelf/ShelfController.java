package com.grocerio.entities.shelf;

import com.grocerio.entities.itemCoordinator.ItemCoordinatorService;
import com.grocerio.entities.listItem.ListItemService;
import com.grocerio.entities.listItem.model.ListItemNew;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.shelf.model.ShelfVm;
import com.grocerio.entities.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelf")
public class ShelfController {
    private final ShelfService shelfService;
    private final UserService userService;

    @Autowired
    public ShelfController(ShelfService shelfService, UserService userService) {
        this.shelfService = shelfService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ShelfVm> getShelf() {
        Shelf shelf = userService.getAuthenticatedUser().shelf;
        shelf = shelfService.generateShareId(shelf.id);
        return ResponseEntity.ok(shelf.toVm());
    }


}
