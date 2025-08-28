package com.mufeng.mufengCommon.config;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class VelocityConfig {
    @Bean
    public VelocityEngine velocityEngine() {
        Properties properties = new Properties();
        // 设置Velocity资源加载器
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(properties);
        return velocityEngine;
    }
}
