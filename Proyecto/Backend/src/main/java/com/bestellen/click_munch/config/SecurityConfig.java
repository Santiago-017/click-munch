package com.bestellen.click_munch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager
                .setUsersByUsernameQuery("select username, password, enabled from users where username=?");
        userDetailsManager
                .setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");
        userDetailsManager
                .setCreateUserSql("insert into users (name, email, username, password, phone, orders) values (?,?,?,?,?,0)");
        userDetailsManager
                .setCreateAuthoritySql("insert into authorities (username, authority) values (?,?)");
        return userDetailsManager;
    }

    @Bean
    @Primary
    public JdbcUserDetailsManager storeDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager storeDetailsManager = new JdbcUserDetailsManager(dataSource);
        storeDetailsManager
                .setUsersByUsernameQuery("select email, password, enabled from stores where email=?");
        // Update the authorities query to use email as the identifier
        storeDetailsManager
                .setAuthoritiesByUsernameQuery("select username, authority from authorities where username=?");
        storeDetailsManager
                .setCreateUserSql("insert into stores (name, alias, email, password, address, latitude, longitude) values (?,?,?,?,?,?,?)");
        storeDetailsManager
                .setCreateAuthoritySql("insert into authorities (username, authority) values (?,?)");
        return storeDetailsManager;
    }


    @Bean
    @Order(1)
    public SecurityFilterChain storeSecurityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {
        // Apply only to endpoints starting with /api/stores/
        http.securityMatcher("/api/stores/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/stores/login", "/api/stores/add-store").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginProcessingUrl("/api/stores/login")
                        .successHandler(authenticationSuccessHandlerForStores())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/stores/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpStatus.OK.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"message\": \"Logged out\" }");
                        })
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                        })
                );

        // Configure authentication manager to use only storeDetailsManager
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(storeDetailsManager(dataSource))
                .passwordEncoder(passwordEncoder());

        return http.build();
    }

    // --- Security filter chain for user endpoints ---
    @Bean
    @Order(2)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {
        // Apply only to endpoints starting with /api/users/
        http.securityMatcher("/api/users/**")
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/login", "/api/users/add-user").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginProcessingUrl("/api/users/login")
                        .successHandler(authenticationSuccessHandlerForUsers())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/users/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpStatus.OK.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{ \"message\": \"Logged out\" }");
                        })
                        .permitAll()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
                        })
                );

        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsManager(dataSource))
                .passwordEncoder(passwordEncoder());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandlerForStores() {
        return (request, response, authentication) -> {
            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            String redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : "/";
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"redirectUrl\": \"" + redirectUrl + "\" }");
        };
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandlerForUsers() {
        return (request, response, authentication) -> {
            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            String redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : "/";
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"redirectUrl\": \"" + redirectUrl + "\" }");
        };
    }

    @Bean
    protected AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"error\": \"Invalid credentials\" }");
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
