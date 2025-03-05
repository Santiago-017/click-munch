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
      return [...prevCart, { ...newItem, quantity: 1, type: newItem.type }];
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

  console.log("📦 Carrito antes de procesar:", cart);


  const placeOrder = async (userId, storeId, paymentMethod) => {
    console.log("Ejecutando placeOrder...");
    console.log("📦 Contenido del carrito antes de procesar:", cart);

    if (!storeId || typeof storeId !== "number") {
        console.error("❌ Error: storeId inválido:", storeId);
        alert("Error: No se identificó la tienda correctamente.");
        return;
    }

    // 🔍 Extraer productos verificando que tengan `type` correctamente definido
    const plateIds = cart.filter(item => item.type?.toLowerCase() === "plate").map(item => item.id);
    const drinkIds = cart.filter(item => item.type?.toLowerCase() === "drink").map(item => item.id);
    const dessertIds = cart.filter(item => item.type?.toLowerCase() === "dessert").map(item => item.id);

    console.log("🍽️ Plates:", plateIds, "🥤 Drinks:", drinkIds, "🍰 Desserts:", dessertIds);

    if (plateIds.length === 0 && drinkIds.length === 0 && dessertIds.length === 0) {
        alert("Error: No se encontraron productos en la orden.");
        return;
    }

    // Calcular total correctamente
    const totalAmount = cart.reduce((sum, item) => sum + (item.price * item.quantity), 0);

    // Crear el JSON para la orden
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

    console.log("✅ Enviando orden con datos corregidos:", orderData);

    try {
        await createOrder(orderData);
        alert("Orden creada exitosamente");
        clearCart();
    } catch (error) {
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
