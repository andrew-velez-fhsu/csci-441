package edu.fhsu.summer.csci441.group1.ZoomBuddy.config;

import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GeometryConfig {
    @Bean
    JtsModule jtsModule() {
        return new JtsModule();
    }
}