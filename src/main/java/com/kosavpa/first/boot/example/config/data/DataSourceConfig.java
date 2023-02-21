package com.kosavpa.first.boot.example.config.data;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfig {
    @Value("${DataSource.driverClassName}")
    private String driverClassName;
    @Value("${DataSource.url}")
    private String url;
    @Value("${DataSource.username}")
    private String username;
    @Value("${DataSource.password}")
    private String password;
    @Bean
    public DataSource dataSource(){
        DataSourceBuilder builder = DataSourceBuilder.create();

        builder
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password);

        return builder.build();
    }
}
