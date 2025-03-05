import React, { useState } from 'react';

function Register() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    username: '',
    password: '',
    phone: '',
  });
  const [errorMessage, setErrorMessage] = useState('');

  // Handle input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Handle form submission: register user and redirect to login page
  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage('');
    const payload = {
      ...formData,
      roles: ['USER'],
    };

    try {
      const response = await fetch('http://localhost:8080/api/users/add-user', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        // Try to parse JSON if available (optional)
        try {
          await response.json();
        } catch (e) {
          console.log('No JSON response to parse.');
        }
        // Redirect to login page after successful registration
        window.location.href = 'http://localhost:3000/loginUser';
      } else {
        // Attempt to parse error details from the response
        try {
          const errorData = await response.json();
          console.error('Error registering user:', errorData);
          setErrorMessage(errorData.message || 'Error registering user.');
        } catch (e) {
          setErrorMessage('Error registering user.');
        }
      }
    } catch (error) {
      console.error('Error during fetch:', error);
      setErrorMessage('Network error or server not reachable.');
    }
  };

  // Inline styles for a cleaner look
  const containerStyle = {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    minHeight: '100vh',
    background: '#f5f5f5',
  };

  const formWrapperStyle = {
    background: '#fff',
    padding: '2rem',
    borderRadius: '8px',
    boxShadow: '0 2px 8px rgba(0, 0, 0, 0.1)',
    width: '100%',
    maxWidth: '400px',
  };

  const inputStyle = {
    width: '100%',
    padding: '0.8rem',
    margin: '0.5rem 0 1rem 0',
    borderRadius: '4px',
    border: '1px solid #ccc',
  };

  const buttonStyle = {
    width: '100%',
    padding: '0.8rem',
    borderRadius: '4px',
    border: 'none',
    background: '#007BFF',
    color: '#fff',
    fontWeight: 'bold',
    cursor: 'pointer',
  };

  const labelStyle = {
    fontWeight: 'bold',
  };

  const errorStyle = {
    color: 'red',
    marginBottom: '1rem',
    textAlign: 'center',
  };

  return (
    <div style={containerStyle}>
      <div style={formWrapperStyle}>
        <h2 style={{ textAlign: 'center', marginBottom: '1.5rem' }}>Register</h2>
        {errorMessage && <div style={errorStyle}>{errorMessage}</div>}
        <form onSubmit={handleSubmit}>
          <div>
            <label style={labelStyle}>Name:</label>
            <input 
              type="text" 
              name="name" 
              value={formData.name} 
              onChange={handleChange} 
              required 
              style={inputStyle}
            />
          </div>
          <div>
            <label style={labelStyle}>Email:</label>
            <input 
              type="email" 
              name="email" 
              value={formData.email} 
              onChange={handleChange} 
              required 
              style={inputStyle}
            />
          </div>
          <div>
            <label style={labelStyle}>Username:</label>
            <input 
              type="text" 
              name="username" 
              value={formData.username} 
              onChange={handleChange} 
              required 
              style={inputStyle}
            />
          </div>
          <div>
            <label style={labelStyle}>Password:</label>
            <input 
              type="password" 
              name="password" 
              value={formData.password} 
              onChange={handleChange} 
              required 
              style={inputStyle}
            />
          </div>
          <div>
            <label style={labelStyle}>Phone:</label>
            <input 
              type="text" 
              name="phone" 
              value={formData.phone} 
              onChange={handleChange} 
              required 
              style={inputStyle}
            />
          </div>
          <button type="submit" style={buttonStyle}>Register</button>
        </form>
      </div>
    </div>
  );
}

export default Register;
