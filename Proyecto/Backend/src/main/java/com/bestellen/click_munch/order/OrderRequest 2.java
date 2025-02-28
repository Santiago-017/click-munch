package com.bestellen.click_munch.order;

import java.util.List;

public record OrderRequest(
        Integer userId,
        Integer storeId,
        List<Integer> plateIds,
        List<Integer> drinkIds,
        List<Integer> dessertIds,
        Double totalAmount,
        Status status,
        Payment paymentMethod

) {
    public Order toOrder() {
        return new Order(null, userId, storeId, null, null, null, totalAmount, status, paymentMethod);
    }
    public Order toOrder(Integer id){
        return new Order(id, userId, storeId, null, null, null, totalAmount, status, paymentMethod);
    }
}
