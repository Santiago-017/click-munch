package com.bestellen.click_munch.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Component
@Order(1)
public class UserJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(UserJsonDataLoader.class);
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    public UserJsonDataLoader(JdbcUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception{
        if (userDetailsManager.userExists("admin")){
            log.info("nothing loaded");
            return;
        }
        try {
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json");
            Users users = objectMapper.readValue(inputStream, Users.class);
            for (User user: users.users()){
                if (userDetailsManager.userExists(user.username())){
                    continue;
                }
                String encodedPassword = passwordEncoder.encode(user.password());
                assert userDetailsManager.getJdbcTemplate() != null;
                userDetailsManager.getJdbcTemplate().update(
                        "insert into users (name, email, username, password, phone, orders) values (?,?,?,?,?,0)",
                        user.name(), user.email(), user.username(), encodedPassword, user.phone()
                );
                userDetailsManager.getJdbcTemplate().update(
                        "insert into authorities (username, authority) values (?,?)",
                        user.username(), "USER"
                );
                log.info("User " + user.username() + " added");
            }
        } catch (IOException e) {
            log.error("Error loading users", e);
        }

}
}
