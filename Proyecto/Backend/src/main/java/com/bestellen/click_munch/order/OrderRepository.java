package com.bestellen.click_munch.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    public Collection<Order> findByStatus(Status status);
    public Collection<Order> findByUserId(Integer userId);
    public Collection<Order> findByStoreId(Integer storeId);

}
