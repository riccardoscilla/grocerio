package com.grocerio.supabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
@Profile("!test")
public class SupabaseConfig {
    private final ConfigurableEnvironment environment;

    public SupabaseConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource dataSource() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("./etc/secrets/supabase.json");
        SupabaseConfigData data = mapper.readValue(jsonFile, SupabaseConfigData.class);

        // Set up HikariCP
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://"+ data.db_host+":"+ data.db_port+"/"+ data.db_name);
        dataSource.setUsername(data.db_user);
        dataSource.setPassword(data.db_password);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

    @PostConstruct
    public void apiProperties() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("./etc/secrets/supabase.json");
        SupabaseConfigData data = mapper.readValue(jsonFile, SupabaseConfigData.class);

        // Set properties in the environment
        environment.getSystemProperties().put("supabase.api_url", data.api_url);
        environment.getSystemProperties().put("supabase.api_key", data.api_key);
        environment.getSystemProperties().put("supabase.api_key_secret", data.api_key_secret);
        environment.getSystemProperties().put("supabase.api_jwt_secret", data.api_jwt_secret);

        // Set properties in the environment
        environment.getSystemProperties().put("render_url", data.render_url);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
