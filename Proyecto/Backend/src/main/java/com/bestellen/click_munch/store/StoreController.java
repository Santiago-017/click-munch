package com.bestellen.click_munch.store;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("")
    public List<Store> findAll() {
        return storeService.findAll();
    }

    @GetMapping("/{id}")
    public Store findById(@PathVariable Integer id) {
        return storeService.findById(id);
    }

    @GetMapping("/{name}")
    public List<Store> findByName(@PathVariable String name) {
        return storeService.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Store store) {
        storeService.create(store);
    }

    @PostMapping("/add-menu")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMenu(@RequestBody StoreRequests storeRequests) {
        storeService.createMenu(storeRequests);
    }

    @PutMapping("")
    public void update(@RequestBody Store store) {
        storeService.update(store);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        storeService.delete(id);
    }

}
