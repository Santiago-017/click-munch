package com.bestellen.click_munch.order;

import com.bestellen.click_munch.menu.DessertService;
import com.bestellen.click_munch.menu.DrinkService;
import com.bestellen.click_munch.menu.PlateService;
import com.bestellen.click_munch.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final PlateService plateService;
    private final DrinkService drinkService;
    private final DessertService dessertService;


    public OrderController(OrderService orderService, UserService userService, PlateService plateService, DrinkService drinkService, DessertService dessertService) {
        this.orderService = orderService;
        this.userService = userService;
        this.plateService = plateService;
        this.drinkService = drinkService;
        this.dessertService = dessertService;
    }

    @GetMapping("")
    public List<Order> findAll() {
        try{
        return orderService.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No orders placed");
        }
    }

    @GetMapping("{username}")
    public List<Order> findByUser(@PathVariable String username){
        if(userService.findByUsername(username) != null) {
            Integer userId = userService.findByUsername(username).id();
            return orderService.findByUserId(userId);
        }else throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Username does not Exist");
    }

    @PostMapping("/new-order")
    public void create(@RequestBody OrderRequest orderRequest) {
        List<Integer> plateIds = orderRequest.plateIds();
        List<Integer> drinkIds = orderRequest.drinkIds();
        List<Integer> dessertIds = orderRequest.dessertIds();
        plateIds.forEach(plate ->{
            Integer storeId = plateService.findStoreByPlate(plate);
            if (!Objects.equals(storeId, orderRequest.storeId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store and Plates don't Correspond");
            }
        });
        drinkIds.forEach(drink->{
            Integer storeId = drinkService.findStoreByDrink(drink);
            if (!Objects.equals(storeId, orderRequest.storeId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store and Drinks don't match");
            }
        });
        dessertIds.forEach(dessert->{
            Integer storeId = dessertService.findStoreByDessert(dessert);
            if(!Objects.equals(storeId, orderRequest.storeId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store and Desserts don't match");
            }
        });
        userService.findById(orderRequest.userId()).orders().add(orderRequest.toOrder());
        userService.updateOrder(orderRequest.userId());
        orderService.createOrder(orderRequest);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Integer id, @RequestBody OrderRequest orderRequest) {
        if(orderService.findById(id).status() == Status.PENDING) {
            orderService.updateOrder(id, orderRequest.toOrder(id));
        }
        else{
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Order is not modifiable anymore");
        }
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        orderService.deleteOrder(id);
    }
}
