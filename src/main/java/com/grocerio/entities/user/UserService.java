package com.grocerio.entities.user;

import com.grocerio.entities.user.model.User;
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

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
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
}
