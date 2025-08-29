package com.mufeng.mufengCommon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 启用 Spring Security 的 Web 安全功能
public class SecurityConfig {

    // 配置密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置安全过滤器链
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/public/**").permitAll() // 允许所有人访问
                        .requestMatchers("/admin/**").hasRole("ADMIN") // 需要 ADMIN 角色
                        .anyRequest().authenticated() // 所有其他请求都需要认证
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login") // 自定义登录页面
                        .permitAll() // 允许所有人访问登录页面
                )
                .logout(logout -> logout
                        .permitAll() // 允许所有人退出登录
                )
                .csrf(csrf -> csrf.disable()); // 对于前后端分离项目，通常禁用 CSRF

        return http.build();
    }

    // 基于内存的用户认证（示例）
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}