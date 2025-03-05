package com.bestellen.click_munch.store;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/stores")
@CrossOrigin(origins="http://localhost:3000")
public class StoreController {

    private final StoreService storeService;
    private final JdbcUserDetailsManager storeDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public StoreController(StoreService storeService, JdbcUserDetailsManager storeDetailsManager, PasswordEncoder passwordEncoder) {
        this.storeService = storeService;
        this.storeDetailsManager = storeDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("")
    public List<Store> findAll() {
        if (storeService.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No stores found");
        }
        return storeService.findAll();
    }

    @GetMapping("/{id}")
    public Store findById(@PathVariable Integer id) {
        try {
            return storeService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
    }

    @GetMapping("/name/{name}")
    public List<Store> findByName(@PathVariable String name) {
        return storeService.findByName(name);
    }

    @PostMapping("/add-store")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Store store) {
        List<Store> tempStore = storeService.findByName(store.name());
        if (!tempStore.isEmpty()) {
            tempStore.forEach(storeSaved -> {
                if(storeSaved.alias().equals(store.alias())){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Store already exists");
                }
            });
        }
        if (storeDetailsManager.userExists(store.email())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(store.password());
        assert storeDetailsManager.getJdbcTemplate() != null;
        storeDetailsManager.getJdbcTemplate().update(
                "insert into stores (name, alias, email, password, address, latitude, longitude) values (?,?,?,?,?,?,?)",
                store.name(), store.alias(), store.email(), encodedPassword, store.address(), store.latitude(), store.longitude()
        );
        storeDetailsManager.getJdbcTemplate().update(
          "insert into authorities (username, authority) values (?,?)",
          store.email(),  "STORE_ADMIN"
        );

    }

    @PostMapping("/add-menu")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenu(@RequestBody StoreRequests storeRequests) {
        storeService.createMenu(storeRequests);
    }

    @PutMapping("{email}")
    public void update(@PathVariable String email, @RequestBody Store store) {
        if(storeService.findByEmail(email)!=null && Objects.equals(store.email(), email)) {
            storeService.update(store);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No match");
        }
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable String email) {
        try {
            storeService.delete(email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store does not exist");
        }
    }

}
