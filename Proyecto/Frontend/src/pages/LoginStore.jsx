// LoginStore.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authAPI } from '../services/api';
import './Login.css';

export default function LoginStore() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const result = await authAPI.loginStore(email, password);
      if (result.success) {
        // Use backend's redirect URL or default to '/'
        const redirectUrl = result.data.redirectUrl || '/';
        navigate(redirectUrl);
      } else {
        console.error("Store login failed:", result.error);
      }
    } catch (error) {
      console.error("Network error:", error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h1 className="login-title">Click &amp; Munch - Store Login</h1>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="login-button">Login as Store</button>
        </form>
      </div>
    </div>
  );
}
