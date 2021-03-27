package com.cqre.cqre;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///c:/Users/leejihoon/Desktop/CQRE/files/galleryImages/");
        registry.addResourceHandler("/images/shop/**")
                .addResourceLocations("file:///c:/Users/leejihoon/Desktop/CQRE/files/shopImages/");
    }
}
