package com.bestellen.click_munch.store;

import java.util.List;

public record StoreRequest(
        Integer storeId,
        List<PlateRequest> plates,
        List<DrinkRequest> drinks,
        List<DessertRequest> desserts
) {
}
