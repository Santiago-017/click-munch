package com.bestellen.click_munch.store;

import com.bestellen.click_munch.menu.Dessert;
import com.bestellen.click_munch.menu.Drink;
import com.bestellen.click_munch.menu.Plate;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("stores")
public record Store(
        @Id
        Integer id,
        @NotEmpty
        String name,
        String alias,
        @NotEmpty
        @Email
        String email,
        String password,
        String address,
        Double latitude,
        Double longitude,
        String logo,
        @MappedCollection(idColumn = "store_id")
        List<Plate> plates,
        @MappedCollection(idColumn = "store_id")
        List<Drink> drinks,
        @MappedCollection(idColumn = "store_id")
        List<Dessert> desserts

) {
}
