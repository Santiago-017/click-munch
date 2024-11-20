import React from 'react';

const Navbar = ({ cartCount }) => {
  return (
    <nav style={{ display: 'flex', justifyContent: 'space-between', padding: '10px', background: '#4caf50', color: 'white' }}>
      <h1>FoodMart</h1>
      <div>🛒 Cart: {cartCount}</div>
    </nav>
  );
};

export default Navbar;
