package com.grocerio.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@DependsOn("supabaseConfig")
public class ScheduledTask {
    private final RestTemplate restTemplate;

    @Value("${render_url}")
    String renderUrl;

    Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    public ScheduledTask(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "0 */10 * * * *")
    public void executeTask() {
        String url = renderUrl + "/scheduled";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        restTemplate.getForEntity(url, Void.class, entity);
    }
}
