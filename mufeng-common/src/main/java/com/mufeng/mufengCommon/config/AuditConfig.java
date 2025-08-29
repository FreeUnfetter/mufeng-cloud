package com.mufeng.mufengCommon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableEnversRepositories
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            // 从Spring Security上下文中获取当前用户名
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("anonymousUser"); // 或 Optional.empty()
            }
            return Optional.of(authentication.getName());
        };
    }
}
