package com.grocerio.supabase;

import com.grocerio.entities.shelf.ShelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private final ShelfService shelfService;


    Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    public ScheduledTask(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void executeTask() {
        logger.info(shelfService.get(1L).toString());
    }
}
