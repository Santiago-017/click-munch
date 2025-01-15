package com.bestellen.click_munch.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Component
@Order(1)
public class UserJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(UserJsonDataLoader.class);
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserJsonDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run(String... args) throws Exception{
        if(userRepository.count() == 0){
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json")) {
                Users users = objectMapper.readValue(inputStream, Users.class);
                log.info("Loaded users data - 8 records");
                userRepository.createAll(users.users());
            }catch (IOException e) {
                throw new RuntimeException("Failed to load users data", e);
            }
        }
        else{
            log.info("Nothing loaded");
        }
}
}
