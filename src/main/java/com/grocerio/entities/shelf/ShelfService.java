package com.grocerio.entities.shelf;

import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.shelf.model.ShelfNew;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

@Service
@Transactional
public class ShelfService {
    private final ShelfRepository shelfRepository;

    Logger logger = LoggerFactory.getLogger(ShelfService.class);

    @Autowired
    public ShelfService(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    public Shelf get(Long id) {
        return shelfRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf does not exists"));
    }

    public Shelf get(String shareId) {
        return shelfRepository.findByShareId(shareId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shelf does not exists"));
    }

    public Shelf save(ShelfNew shelfNew) {
        Shelf shelf = new Shelf();
        shelf.name = shelfNew.name;

        return shelfRepository.save(shelf);
    }

    public Shelf generateShareId(Long id) {
        Shelf shelf = this.get(id);
        shelf.shareId = ShelfService.generateShortUUID();
        return shelfRepository.save(shelf);
    }

    public static String generateShortUUID() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
    }


}
