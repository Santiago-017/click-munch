package com.bestellen.click_munch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, DataSource dataSource) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsManager(dataSource))
                .passwordEncoder(passwordEncoder());
        authBuilder.userDetailsService(storeDetailsManager(dataSource))
                .passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }

    @Bean
    JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager
                .setUsersByUsernameQuery(
                        "select username, password, enabled from users where username=?"
                );
        userDetailsManager
                .setAuthoritiesByUsernameQuery(
                        "select username, authority from authorities where username=?"
                );
        userDetailsManager
                .setCreateUserSql(
                        "insert into users (name, email, username, password, phone, orders) values (?,?,?,?,?,0)"
                );
        userDetailsManager
                .setCreateAuthoritySql(
                        "insert into authorities (username, authority) values (?,?)"
                );

        return userDetailsManager;
    }

    @Bean
    JdbcUserDetailsManager storeDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager storeDetailsManager = new JdbcUserDetailsManager(dataSource);
        storeDetailsManager
                .setUsersByUsernameQuery(
                        "select email, password, enabled from stores where email=?"
                );
        storeDetailsManager
                .setAuthoritiesByUsernameQuery(
                        "select username, authority from authorities where username=?"
                );
        storeDetailsManager
                .setCreateUserSql(
                        "insert into stores (name, alias, email, password, address, latitude, longitude) values (?,?,?,?,?,?,?)"
                );
        storeDetailsManager
                .setCreateAuthoritySql(
                        "insert into authorities (username, authority) values (?,?)"
                );

        return storeDetailsManager;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/add-user").permitAll()
                        .requestMatchers("/api/stores/add-store").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .build();
    }

}
