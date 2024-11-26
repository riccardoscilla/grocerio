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
    private SupabaseConfigData configData;

    public SupabaseConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File("./etc/secrets/supabase.json");
        configData = mapper.readValue(jsonFile, SupabaseConfigData.class);
    }

    @Bean
    public DataSource dataSource() {
//        ObjectMapper mapper = new ObjectMapper();
//        File jsonFile = new File("./etc/secrets/supabase.json");
//        SupabaseConfigData data = mapper.readValue(jsonFile, SupabaseConfigData.class);

        // Set up HikariCP
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://"+ configData.db_host+":"+ configData.db_port+"/"+ configData.db_name);
        dataSource.setUsername(configData.db_user);
        dataSource.setPassword(configData.db_password);
        dataSource.setDriverClassName("org.postgresql.Driver");

        return dataSource;
    }

    @Bean
    public String apiProperties() {
//        ObjectMapper mapper = new ObjectMapper();
//        File jsonFile = new File("./etc/secrets/supabase.json");
//        SupabaseConfigData data = mapper.readValue(jsonFile, SupabaseConfigData.class);

        // Set properties in the environment
        environment.getSystemProperties().put("supabase.api_url", configData.api_url);
        environment.getSystemProperties().put("supabase.api_key", configData.api_key);
        environment.getSystemProperties().put("supabase.api_key_secret", configData.api_key_secret);
        environment.getSystemProperties().put("supabase.api_jwt_secret", configData.api_jwt_secret);

        // Set properties in the environment
        environment.getSystemProperties().put("render_url", configData.render_url);

        return  "";
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
