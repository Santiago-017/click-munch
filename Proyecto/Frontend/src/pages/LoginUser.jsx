import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authAPI } from '../services/api';
import './Login.css';

export default function LoginUser() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const result = await authAPI.login(username, password);
      if (result.success) {
        // Use backend's redirect URL or default to '/'
        const redirectUrl = result.data.redirectUrl || '/';
        navigate(redirectUrl);
      } else {
        console.error("User login failed:", result.error);
      }
    } catch (error) {
      console.error("Network error:", error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-form">
        <h1 className="login-title">Click &amp; Munch - User Login</h1>
        <form onSubmit={handleSubmit}>
          <div className="input-group">
            <input
              type="text"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
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
          <button type="submit" className="login-button">Login</button>
        </form>
        <div className="register-link" style={{ marginTop: '1rem', textAlign: 'center' }}>
          ¿No tienes una cuenta? <Link to="/registerUser">Regístrate</Link>
        </div>
      </div>
    </div>
  );
}
