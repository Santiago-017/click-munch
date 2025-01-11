package com.bestellen.click_munch.menu;

import com.bestellen.click_munch.store.Store;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("plates")
public record Plate(
        @Id
        Integer id,
        @Column("store_id")
        Integer storeId,
        @Column("stores_key")
        Integer key,
        String name,
        String description,
        Double price,
        String image
) {
}
