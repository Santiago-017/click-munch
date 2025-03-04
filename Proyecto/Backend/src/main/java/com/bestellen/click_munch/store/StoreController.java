package com.bestellen.click_munch.store;

import org.springframework.http.HttpStatus;
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

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
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

    @PostMapping("")
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
        storeService.create(store);
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
