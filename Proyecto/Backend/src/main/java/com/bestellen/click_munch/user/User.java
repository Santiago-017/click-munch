package com.bestellen.click_munch.user;

import com.bestellen.click_munch.order.Order;
import com.bestellen.click_munch.store.Store;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;

@Table("users")
public record User(
        @Id
        Integer id,
        @NotEmpty
        String name,
        @Email
        @NotEmpty
        String email,
        @NotEmpty
        String username,
        @NotEmpty
        String password,
        @NotEmpty
        String phone,
        @MappedCollection(idColumn = "user_id")
        Set<Order> orders)
        //Set<Store> Favorites)
        {

}