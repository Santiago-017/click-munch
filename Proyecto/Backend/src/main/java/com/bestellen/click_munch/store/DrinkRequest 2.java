package com.bestellen.click_munch.store;

public record DrinkRequest(
        String name,
        String description,
        Double price,
        String image
) {
}
