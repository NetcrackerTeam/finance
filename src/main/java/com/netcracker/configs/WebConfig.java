package com.netcracker.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.netcracker")
@PropertySource("classpath:application.properties")
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private Environment env;

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(env.getProperty("jsp.prefix"));
        resolver.setSuffix(env.getProperty("jsp.suffix"));
        return resolver;
    }
}
