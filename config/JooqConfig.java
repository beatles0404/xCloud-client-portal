package com.lenovo.sap.api.config;

import org.jooq.conf.Settings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig{
    public Settings settings() {
        return new Settings()
                .withRenderCatalog(false)
                .withRenderSchema(false);
    }
}
