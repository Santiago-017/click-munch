package com.bestellen.click_munch.menu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DessertRepository extends CrudRepository<Dessert, Integer> {
}
