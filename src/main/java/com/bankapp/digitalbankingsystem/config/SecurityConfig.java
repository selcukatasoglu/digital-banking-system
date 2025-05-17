package com.bankapp.digitalbankingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Security yapılandırma sınıfı.
 * Uygulama güvenlik ayarlarını, kimlik doğrulama ve yetkilendirme kurallarını tanımlar.
 * Login, logout ve erişim kontrolü işlemlerini yönetir.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Güvenlik filtre zincirini yapılandırır
     * @param http HTTP güvenlik yapılandırması
     * @return Yapılandırılmış güvenlik filtre zinciri
     * @throws Exception Yapılandırma hatası durumunda
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/register",
                    "/login",
                    "/error",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            );
        
        return http.build();
    }

    /**
     * Şifre şifreleme için BCrypt encoder'ı sağlar
     * @return BCrypt şifre şifreleyici
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Kimlik doğrulama yöneticisini yapılandırır
     * @param authConfig Kimlik doğrulama yapılandırması
     * @return Kimlik doğrulama yöneticisi
     * @throws Exception Yapılandırma hatası durumunda
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
} 