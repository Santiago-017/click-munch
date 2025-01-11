package com.bestellen.click_munch.order;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    public final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @PostMapping("/new-order")
    public void create(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody Order order) {
        orderService.updateOrder(id, order);
    }
}
