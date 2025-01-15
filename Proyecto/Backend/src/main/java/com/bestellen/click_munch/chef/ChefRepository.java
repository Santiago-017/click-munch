package com.bestellen.click_munch.chef;

import com.bestellen.click_munch.order.Order;
import com.bestellen.click_munch.order.Status;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChefRepository extends CrudRepository<Chef, Integer> {

    @Modifying
    @Query("UPDATE orders SET status = :status WHERE id = :orderId")
    void changeOrderStatus(@Param("orderId") Integer orderId,
                           @Param("status") Status status);

    @Query("SELECT * FROM orders WHERE status = :status AND store_id = :storeId")
    Collection<Order> findOrdersByStatus(@Param("status")Status status,
                                         @Param("storeId")Integer storeId);
}
