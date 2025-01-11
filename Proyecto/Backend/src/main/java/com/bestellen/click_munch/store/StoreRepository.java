package com.bestellen.click_munch.store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StoreRepository extends CrudRepository<Store, Integer> {

    public Collection<Store> findByName(String name);
    default void createAll(List<Store> stores){
        this.saveAll(stores);
    };

}
