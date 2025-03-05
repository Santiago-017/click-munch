// axiosInstance.js
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
  withCredentials: true, // Ensure cookies/sessions are sent with each request
});

// Interceptor to catch unauthorized responses
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const originalRequest = error.config;
    // Define login endpoints to exclude from redirection
    const loginEndpoints = ["/users/login", "/stores/login", "/login"];
    const isNotLoginRequest = !loginEndpoints.some(endpoint => originalRequest.url.includes(endpoint));
    
    if (error.response && error.response.status === 401 && isNotLoginRequest) {
      // Save the originally requested URL
      const intendedUrl = window.location.pathname;
      sessionStorage.setItem("intendedUrl", intendedUrl);
      
      // Decide which login page to redirect to based on the API endpoint
      if (originalRequest.url.includes("/users/")) {
        window.location.href = "/loginUser";
      } else if (originalRequest.url.includes("/stores/")) {
        window.location.href = "/loginStore";
      } else {
        // Default fallback
        window.location.href = "/loginUser";
      }
    }
    return Promise.reject(error);
  }
);

export default api;
