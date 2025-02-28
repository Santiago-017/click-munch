package com.bestellen.click_munch.chef;

import com.bestellen.click_munch.menu.Dessert;
import com.bestellen.click_munch.menu.Drink;
import com.bestellen.click_munch.menu.Plate;
import com.bestellen.click_munch.order.Order;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Map;

@Table("chefs")
public record Chef(
        @Id
        Integer id,
        Integer storeId,
        List<Order> orderList,
        Map<Plate, Integer> availablePlates,
        Map<Drink, Integer> availableDrinks,
        Map<Dessert, Integer> availableDesserts
) {
}
