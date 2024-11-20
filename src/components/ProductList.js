import React from 'react';

const products = [
  { id: 1, name: 'Apple', price: 1.5 },
  { id: 2, name: 'Banana', price: 1.0 },
  { id: 3, name: 'Carrot', price: 0.8 },
];

const ProductList = ({ addToCart }) => {
  return (
    <div style={{ padding: '20px' }}>
      <h2>Products</h2>
      <ul style={{ listStyleType: 'none', padding: 0 }}>
        {products.map((product) => (
          <li key={product.id} style={{ marginBottom: '10px' }}>
            <div>
              {product.name} - ${product.price.toFixed(2)}
              <button
                onClick={() => addToCart(product)}
                style={{
                  marginLeft: '10px',
                  padding: '5px 10px',
                  backgroundColor: '#4caf50',
                  color: 'white',
                  border: 'none',
                  cursor: 'pointer',
                }}
              >
                Add to Cart
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ProductList;
