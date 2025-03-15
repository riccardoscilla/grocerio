package com.grocerio.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

@Component
@DependsOn("supabaseConfig")
public class ScheduledTask {
    private final RestTemplate restTemplate;
    private Instant lastSuccessfulCall = Instant.MIN;

    @Value("${render_url}")
    String renderUrl;

    Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    public ScheduledTask(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 * * * * *")
    public void executeTask() {
        if (Duration.between(lastSuccessfulCall, Instant.now()).toMinutes() < 10)
            return;

        String url = renderUrl + "/scheduled";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.GET, entity, Void.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                lastSuccessfulCall = Instant.now();
            }
        } catch (Exception e) {
            logger.error("Scheduled task failed: {}", e.getMessage());
        }
    }
}
