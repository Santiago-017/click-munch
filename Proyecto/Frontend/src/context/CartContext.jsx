import { createContext, useContext, useState } from "react";

const CartContext = createContext();

export function CartProvider({ children }) {
    const [cart, setCart] = useState([]);

    const addToCart = (newItem) => {
        setCart((prevCart) => {
            const existingItem = prevCart.find(item => item.id === newItem.id);
            if (existingItem) {
                return prevCart.map(item =>
                    item.id === newItem.id ? { ...item, quantity: item.quantity + 1 } : item
                );
            }
            return [...prevCart, { ...newItem, quantity: 1 }];
        });
    };

    const removeFromCart = (itemId) => {
        setCart((prevCart) => prevCart.filter(item => item.id !== itemId));
    };

    const decreaseQuantity = (itemId) => {
        setCart((prevCart) =>
            prevCart.map(item =>
                item.id === itemId ? { ...item, quantity: Math.max(1, item.quantity - 1) } : item
            )
        );
    };

    const clearCart = () => {
        setCart([]);
    };

    return (
        <CartContext.Provider value={{ cart, addToCart, removeFromCart, decreaseQuantity, clearCart }}>
            {children}
        </CartContext.Provider>
    );
}

export function useCart() {
    return useContext(CartContext);
}
