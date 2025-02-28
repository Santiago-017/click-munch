package com.bestellen.click_munch.order;

import com.bestellen.click_munch.menu.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final PlateRepository plateRepository;
    private final OrderPlateRepository orderPlateRepository;
    private final DrinkRepository drinkRepository;
    private final OrderDrinkRepository orderDrinkRepository;
    private final DessertRepository dessertRepository;
    private final OrderDessertRepository orderDessertRepository;

    public OrderService(OrderRepository orderRepository,
                        PlateRepository plateRepository, OrderPlateRepository orderPlateRepository,
                        DrinkRepository drinkRepository, OrderDrinkRepository orderDrinkRepository,
                        DessertRepository dessertRepository, OrderDessertRepository orderDessertRepository) {
        this.orderRepository = orderRepository;
        this.plateRepository = plateRepository;
        this.orderPlateRepository = orderPlateRepository;
        this.drinkRepository = drinkRepository;
        this.orderDrinkRepository = orderDrinkRepository;
        this.dessertRepository = dessertRepository;
        this.orderDessertRepository = orderDessertRepository;
    }

    public List<Order> findAll() {
        return (List<Order>) orderRepository.findAll();
    }

    public List<Order> findByUserId(Integer userId) {
        return (List<Order>) orderRepository.findByUserId(userId);
    }

    public void createOrder(OrderRequest orderRequest) {
        Order savedOrder = orderRepository.save(orderRequest.toOrder());
        orderRequest.plateIds().forEach(plateId -> {
            Optional<Plate> plate = plateRepository.findById(plateId);
            plate.ifPresent(value -> orderPlateRepository.save(new OrderPlate( null, savedOrder.id(), value.id())));
        });
        orderRequest.drinkIds().forEach(drinkId -> {
            Optional<Drink> drink = drinkRepository.findById(drinkId);
            drink.ifPresent(value -> orderDrinkRepository.save(new OrderDrink( null, savedOrder.id(), value.id())));
        });
        orderRequest.dessertIds().forEach(dessertId -> {
            Optional<Dessert> dessert = dessertRepository.findById(dessertId);
            dessert.ifPresent(value -> orderDessertRepository.save(new OrderDessert( null, savedOrder.id(), value.id())));
        });
    }

    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }

    public void updateOrder(Integer id, Order order) {
        if(orderRepository.findById(id).isPresent()) {
            orderRepository.save(order);
        }
    }
    public List<Order> findByStatus(Status status, Integer storeId) {
        List<Order> ordersByStatus=(List<Order>) orderRepository.findByStatus(status);
        return ordersByStatus.stream()
                .filter(order -> order.storeId().equals(storeId))
                .toList();
//        return (List<Order>) orderRepository.findByStatus(status);
    }

    public List<Order> findByStoreId(Integer storeId) {
        return (List<Order>) orderRepository.findByStoreId(storeId);
    }

}
