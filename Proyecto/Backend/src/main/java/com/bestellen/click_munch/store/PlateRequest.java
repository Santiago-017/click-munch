package com.bestellen.click_munch.store;

public record PlateRequest(
        String name,
        String description,
        Double price,
        String image
) {
}
