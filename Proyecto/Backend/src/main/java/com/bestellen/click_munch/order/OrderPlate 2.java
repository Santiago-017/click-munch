package com.bestellen.click_munch.order;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("order_plates")
public record OrderPlate(
        @Id
        Integer id,
        Integer orderId,
        Integer plateId

) {
}
