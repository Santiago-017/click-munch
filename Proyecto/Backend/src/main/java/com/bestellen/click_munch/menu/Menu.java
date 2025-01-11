package com.bestellen.click_munch.menu;

import java.util.Set;

public record Menu(
        Integer id,
        Integer storeId,
        Set<Plate> plates,
        Set<Drink> drinks,
        Set<Dessert> desserts
) {
}
