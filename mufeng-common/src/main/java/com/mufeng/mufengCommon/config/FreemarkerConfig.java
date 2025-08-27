package com.mufeng.mufengCommon.config;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {

    @Bean
    public Configuration freemarkerConfiguration() throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        return configuration;
    }
}