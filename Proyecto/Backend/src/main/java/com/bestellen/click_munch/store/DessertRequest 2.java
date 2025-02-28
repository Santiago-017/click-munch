package com.bestellen.click_munch.store;

public record DessertRequest(
        String name,
        String description,
        Double price,
        String image
) {
}
