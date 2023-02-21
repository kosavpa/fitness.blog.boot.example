package com.kosavpa.first.boot.example.config.web;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;


@Configuration
public class WebConfig {
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(1024 * 1024 * 4);

        return multipart;
    }

    @Bean
    @Order(0)
    public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("multipartResolver");

        return multipartFilter;
    }
}
