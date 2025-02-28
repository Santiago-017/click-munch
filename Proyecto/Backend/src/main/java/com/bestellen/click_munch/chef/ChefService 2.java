package com.bestellen.click_munch.chef;

import com.bestellen.click_munch.order.Order;
import com.bestellen.click_munch.order.Status;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ChefService {

    private final ChefRepository chefRepository;

    public ChefService(ChefRepository chefRepository) {
        this.chefRepository = chefRepository;
    }

    public void changeOrderStatus(Integer orderId, Status status) {
        chefRepository.changeOrderStatus(orderId, status);
    }

    public Collection<Order> findOrdersByStatus(Status status, Integer storeId){
        return chefRepository.findOrdersByStatus(status, storeId);
    }

}
