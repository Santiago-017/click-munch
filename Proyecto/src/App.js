import React, { useState } from 'react';
import Navbar from './components/Navbar';
import ProductList from './components/ProductList';
import Cart from './components/Cart';

const App = () => {
  const [cartItems, setCartItems] = useState([]);

  const addToCart = (product) => {
    setCartItems([...cartItems, product]);
  };

  return (
    <div>
      <Navbar cartCount={cartItems.length} />
      <div className="container">
        <ProductList addToCart={addToCart} />
        <Cart cartItems={cartItems} />
      </div>
    </div>
  );
};

export default App;
