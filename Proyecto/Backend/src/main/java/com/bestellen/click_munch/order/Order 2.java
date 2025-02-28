package com.bestellen.click_munch.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;


@Table("orders")
public record Order(
        @Id
        Integer id,
        @Column("user_id")
        Integer userId,
        @Column("store_id")
        Integer storeId,
        @MappedCollection(idColumn = "order_id")
        Set<OrderPlate> plates,
        @MappedCollection(idColumn = "order_id")
        Set<OrderDrink> drinks,
        @MappedCollection(idColumn = "order_id")
        Set<OrderDessert> desserts,
        @Column("total_price")
        Double total,
        Status status,
        @Column("payment_method")
        Payment paymentMethod
) {
}
