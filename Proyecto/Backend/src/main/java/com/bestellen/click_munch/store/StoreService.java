package com.bestellen.click_munch.store;

import com.bestellen.click_munch.menu.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final PlateRepository plateRepository;
    private final DrinkRepository drinkRepository;
    private final DessertRepository dessertRepository;

    public StoreService(StoreRepository storeRepository, PlateRepository plateRepository, DrinkRepository drinkRepository, DessertRepository dessertRepository) {
        this.storeRepository = storeRepository;
        this.plateRepository = plateRepository;
        this.drinkRepository = drinkRepository;
        this.dessertRepository = dessertRepository;
    }

    public List<Store> findAll() {
        return (List<Store>) storeRepository.findAll();
    }

    public Store findById(Integer id) {
        return storeRepository.findById(id)
                .map(store -> store)
                .orElseThrow();
    }

    public List<Store> findByName(String name) {
        return (List<Store>) storeRepository.findByName(name);
    }

    public void create(Store store) {
        storeRepository.save(store);
    }

    public void createMenu(StoreRequests storeRequests) {
        for (StoreRequest menu : storeRequests.storeRequests()){
            Integer storeId = menu.storeId();
            Store store = storeRepository.findById(storeId).orElseThrow(() -> new RuntimeException("Store not found"));

            List<Plate> plates = menu.plates().stream()
                    .map(plateRequest -> new Plate(null, store.id(), null, plateRequest.name(), plateRequest.description(), plateRequest.price(), plateRequest.image()))
                            .toList();
            plateRepository.saveAll(plates);

            List<Drink> drinks = menu.drinks().stream()
                    .map(drinkRequest -> new Drink(null, store.id(), null, drinkRequest.name(), drinkRequest.description(), drinkRequest.price(), drinkRequest.image()))
                            .toList();
            drinkRepository.saveAll(drinks);

            List<Dessert> desserts = menu.desserts().stream()
                    .map(dessertRequest -> new Dessert(null, store.id(), null, dessertRequest.name(), dessertRequest.description(), dessertRequest.price(), dessertRequest.image()))
                            .toList();
            dessertRepository.saveAll(desserts);
        }
    }

    public void update(Store store) {
        storeRepository.save(store);
    }

    public void delete(Integer id) {
        storeRepository.deleteById(id);
    }
}
