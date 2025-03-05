// src/context/CartContext.jsx
import { createContext, useContext, useState } from "react";
import { createOrder } from "../services/api";

const CartContext = createContext();

export function CartProvider({ children }) {
  const [cart, setCart] = useState([]);

  const addToCart = (newItem) => {
    setCart((prevCart) => {
      const existingItem = prevCart.find((item) => item.id === newItem.id);
      if (existingItem) {
        return prevCart.map((item) =>
          item.id === newItem.id ? { ...item, quantity: item.quantity + 1 } : item
        );
      }
      return [...prevCart, { ...newItem, quantity: 1 }];
    });
  };

  const removeFromCart = (itemId) => {
    setCart((prevCart) => prevCart.filter((item) => item.id !== itemId));
  };

  const decreaseQuantity = (itemId) => {
    setCart((prevCart) =>
      prevCart.map((item) =>
        item.id === itemId ? { ...item, quantity: Math.max(1, item.quantity - 1) } : item
      )
    );
  };

  const clearCart = () => {
    setCart([]);
  };

  const placeOrder = async (storeId, paymentMethod) => {
    try {
      console.log("Ejecutando placeOrder...");
  
      const userId = 1; // 🔴 Usuario predeterminado
  
      const plateIds = cart.filter(item => item.type === "plate").map(item => item.id);
      const drinkIds = cart.filter(item => item.type === "drink").map(item => item.id);
      const dessertIds = cart.filter(item => item.type === "dessert").map(item => item.id);
      const totalAmount = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);
  
      const orderData = {
        userId,
        storeId,
        plateIds,
        drinkIds,
        dessertIds,
        totalAmount,
        status: "PENDING",
        paymentMethod,
      };
  
      console.log("Enviando orden:", orderData); // 🔴 Verifica en la consola
  
      await createOrder(orderData);
      alert("Orden creada exitosamente");
      clearCart();
    } catch (error) {
      console.error("Error al crear la orden:", error);
      alert("Error al crear la orden: " + error.message);
    }
  };
  


  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart, decreaseQuantity, clearCart, placeOrder }}>
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  return useContext(CartContext);
}
