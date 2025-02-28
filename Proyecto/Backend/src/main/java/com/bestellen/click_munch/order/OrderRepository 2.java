package com.bestellen.click_munch.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

     Collection<Order> findByStatus(Status status);
     Collection<Order> findByUserId(Integer userId);
     Collection<Order> findByStoreId(Integer storeId);




}
