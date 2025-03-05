package com.bestellen.click_munch.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

@Component
@Order(1)
public class StoreJsonDataLoader implements CommandLineRunner {

    private static final Logger log = Logger.getLogger(StoreJsonDataLoader.class.getName());
    private final JdbcUserDetailsManager storeDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final JdbcTemplate jdbcTemplate;

    public StoreJsonDataLoader(JdbcUserDetailsManager storeDetailsManager, PasswordEncoder passwordEncoder, ObjectMapper objectMapper, JdbcTemplate jdbcTemplate) {
        this.storeDetailsManager = storeDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception{
        if(storeDetailsManager.userExists("admin")){
            log.info("nothing loaded");
            return;
        }
        try{
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/stores.json");
            Stores stores = objectMapper.readValue(inputStream, Stores.class);
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM stores", Integer.class);
            if (count != null && count > 0) {
                log.info("Nothing loaded.");
                return;
            }
            for (Store store: stores.stores()){
                String encodedPassword = passwordEncoder.encode(store.password());
                assert storeDetailsManager.getJdbcTemplate() != null;
                storeDetailsManager.getJdbcTemplate().update(
                        "insert into stores (name, alias, email, password, address, latitude, longitude) values (?,?,?,?,?,?,?)",
                        store.name(), store.alias(), store.email(), encodedPassword, store.address(), store.latitude(), store.longitude());
                storeDetailsManager.getJdbcTemplate().update(
                        "insert into authorities (username, authority) values (?,?)",
                        store.email(), "STORE_ADMIN"
                );
                log.info("Store " + store.alias() + " added");
            }
        }
        catch (IOException e){
            log.info("Error loading stores");
        }
    }
}
