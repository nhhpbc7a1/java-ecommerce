package com.ecommerce.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api",
                c -> c.isAnnotationPresent(RestController.class));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Áp dụng cho tất cả API endpoints
                .allowedOrigins("http://localhost:3000")  // Cho phép frontend React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Các HTTP methods
                .allowedHeaders("*")  // Cho phép tất cả headers
                .allowCredentials(true)  // Cho phép gửi cookies/credentials
                .maxAge(3600);  // Cache preflight request trong 1 giờ
    }
}