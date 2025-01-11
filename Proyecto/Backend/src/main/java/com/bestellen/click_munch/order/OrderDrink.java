package com.bestellen.click_munch.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("order_drinks")
public record OrderDrink(
        @Id
        Integer id,
        Integer orderId,
        Integer drinkId
) {
}
