package com.grocerio.entities.user;

import com.grocerio.entities.listItem.model.ListItemNew;
import com.grocerio.entities.shelf.model.Shelf;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join-shelf")
    public ResponseEntity<Void> joinShelf(
            @RequestBody String shareId
    ) {
        userService.joinShelf(shareId);
        return ResponseEntity.ok().build();
    }
}
