import React from 'react';

const Cart = ({ cartItems }) => {
  const total = cartItems.reduce((acc, item) => acc + item.price, 0);

  return (
    <div style={{ padding: '20px' }}>
      <h2>Cart</h2>
      {cartItems.length === 0 ? (
        <p>Your cart is empty</p>
      ) : (
        <ul style={{ listStyleType: 'none', padding: 0 }}>
          {cartItems.map((item, index) => (
            <li key={index} style={{ marginBottom: '10px' }}>
              {item.name} - ${item.price.toFixed(2)}
            </li>
          ))}
        </ul>
      )}
      <h3>Total: ${total.toFixed(2)}</h3>
    </div>
  );
};
 
export default Cart;
