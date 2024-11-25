package com.grocerio.entities.shelf;

import com.grocerio.entities.shelf.model.Shelf;
import com.grocerio.entities.shelf.model.ShelfNew;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Shelf save(ShelfNew shelfNew) {
        Shelf shelf = new Shelf();
        shelf.name = shelfNew.name;

        return shelfRepository.save(shelf);
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void executeTask() {
        logger.info(shelfRepository.findAll().toString());
    }

}
