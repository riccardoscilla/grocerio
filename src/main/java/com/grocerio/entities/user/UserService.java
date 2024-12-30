package com.grocerio.entities.user;

import com.grocerio.entities.shelf.ShelfService;
import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.user.model.User;
import com.grocerio.entities.user.model.UserNew;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Transactional
public class UserService {
    private final ShelfService shelfService;
    private final UserRepository userRepository;

    @Autowired
    public UserService(ShelfService shelfService, UserRepository userRepository) {
        this.shelfService = shelfService;
        this.userRepository = userRepository;
    }

    public User getById(UUID uuid) {
        return this.userRepository.findById(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exists"));
    }

    public User getAuthenticatedUser() {
        String principal = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID uuid = UUID.fromString(principal);
        return getById(uuid);
    }

    public void save(UserNew userNew) {
        User user = new User();
        user.uuid = userNew.uuid;
        user.shelf = shelfService.get(userNew.shelfId);
        this.userRepository.save(user);
    }

    public void joinShelf(String shareId) {
        User user = getAuthenticatedUser();
        user.shelf = shelfService.get(shareId);
        this.userRepository.save(user);
    }

}
