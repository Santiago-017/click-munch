package com.bestellen.click_munch.chef;

import com.bestellen.click_munch.order.Order;
import com.bestellen.click_munch.order.OrderService;
import com.bestellen.click_munch.order.Status;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/chef")
public class ChefController {
    private final ChefService chefService;
    private final OrderService orderService;

    public ChefController(ChefService chefService, OrderService orderService) {
        this.chefService = chefService;
        this.orderService = orderService;
    }



    @PatchMapping("/{id}")
    public void changeOrderStatus(@PathVariable Integer id, @RequestBody Status status){
        if(orderService.findById(id)!=null) {
            chefService.changeOrderStatus(id, status);
        }
    }

}
