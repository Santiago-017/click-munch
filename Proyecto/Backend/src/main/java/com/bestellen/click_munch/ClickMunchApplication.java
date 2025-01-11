package com.bestellen.click_munch;

import com.bestellen.click_munch.menu.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.List;

@SpringBootApplication
public class ClickMunchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClickMunchApplication.class, args);

	}
/*
	//@Bean
	//@Order(2)
	ApplicationRunner applicationRunner(PlateRepository plateRepository, DrinkRepository drinkRepository, DessertRepository dessertRepository) {
		return args -> {

			var platesUno = getPlates();
			var drinksUno = getDrinks();
			var dessertsUno = getDesserts();

			plateRepository.saveAll(platesUno);
			drinkRepository.saveAll(drinksUno);
			dessertRepository.saveAll(dessertsUno);
		};

	}

	private static List<Drink> getDrinks() {
		Drink d1 = new Drink(null, 1, "Coke", "Coca Cola", 2.00, "coke.jpg");
		Drink d2 = new Drink(null, 1, "Pepsi", "Pepsi Cola", 2.00, "pepsi.jpg");
		Drink d3 = new Drink(null, 1, "Sprite", "Sprite Soda", 2.00, "sprite.jpg");
		Drink d4 = new Drink(null, 1, "Water", "Bottled Water", 1.00, "water.jpg");
		Drink d5 = new Drink(null, 1, "Tea", "Sweet Tea", 2.00, "tea.jpg");
		Drink d6 = new Drink(null, 1, "Coffee", "Black Coffee", 2.00, "coffee.jpg");
		Drink d7 = new Drink(null, 1, "Milk", "Whole Milk", 2.00, "milk.jpg");
		Drink d8 = new Drink(null, 1, "Juice", "Orange Juice", 2.00, "juice.jpg");
		Drink d9 = new Drink(null, 1, "Beer", "Craft Beer", 5.00, "beer.jpg");
		Drink d10 = new Drink(null, 1, "Wine", "Red Wine", 8.00, "wine.jpg");

		return List.of(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10);
	}

	private static List<Plate> getPlates() {
		Plate p1 = new Plate(null, 1, "Rice and Beans", "Rice and Beans with Chicken", 10.00, "rice_and_beans.jpg");
		Plate p2 = new Plate(null, 1, "Spaghetti", "Spaghetti with Meatballs", 12.00, "spaghetti.jpg");
		Plate p3 = new Plate(null, 1, "Pizza", "Pepperoni Pizza", 15.00, "pizza.jpg");
		Plate p4 = new Plate(null, 1, "Burger", "Cheeseburger with Fries", 8.00, "burger.jpg");
		Plate p5 = new Plate(null, 1, "Tacos", "Tacos with Guacamole", 10.00, "tacos.jpg");
		Plate p6 = new Plate(null, 1, "Salad", "Caesar Salad", 7.00, "salad.jpg");
		Plate p7 = new Plate(null, 1, "Sushi", "Sushi Rolls", 20.00, "sushi.jpg");
		Plate p8 = new Plate(null, 1, "Pasta", "Pasta Alfredo", 12.00, "pasta.jpg");
		Plate p9 = new Plate(null, 1, "Sandwich", "Turkey Sandwich", 6.00, "sandwich.jpg");
		Plate p10 = new Plate(null, 1, "Soup", "Chicken Noodle Soup", 5.00, "soup.jpg");

		return List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);
	}

	private static List<Dessert> getDesserts() {
		Dessert de1 = new Dessert(null, 1, "Cake", "Chocolate Cake", 5.00, "cake.jpg");
		Dessert de2 = new Dessert(null, 1, "Ice Cream", "Vanilla Ice Cream", 3.00, "ice_cream.jpg");
		Dessert de3 = new Dessert(null, 1, "Cookies", "Chocolate Chip Cookies", 2.00, "cookies.jpg");
		Dessert de4 = new Dessert(null, 1, "Pie", "Apple Pie", 4.00, "pie.jpg");
		Dessert de5 = new Dessert(null, 1, "Brownie", "Fudge Brownie", 3.00, "brownie.jpg");
		Dessert de6 = new Dessert(null, 1, "Donut", "Glazed Donut", 2.00, "donut.jpg");
		Dessert de7 = new Dessert(null, 1, "Cupcake", "Red Velvet Cupcake", 3.00, "cupcake.jpg");
		Dessert de8 = new Dessert(null, 1, "Pudding", "Chocolate Pudding", 3.00, "pudding.jpg");
		Dessert de9 = new Dessert(null, 1, "Candy", "Gummy Bears", 1.00, "candy.jpg");
		Dessert de10 = new Dessert(null, 1, "Fruit", "Fruit Salad", 4.00, "fruit.jpg");

		return List.of(de1, de2, de3, de4, de5, de6, de7, de8, de9, de10);
	}

 */
}
