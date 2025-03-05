// api.js
import api from "./axiosInstance";

// Obtener todos los restaurantes
export const getRestaurants = async () => {
  try {
    const response = await api.get("/stores");
    return response.data;
  } catch (error) {
    throw new Error("Error al obtener los restaurantes: " + error.message);
  }
};

// Obtener el menú de un restaurante por ID
export const getRestaurantById = async (id) => {
  try {
    const response = await api.get(`/stores/${id}`);
    return response.data;
  } catch (error) {
    throw new Error("Error al obtener el restaurante: " + error.message);
  }
};

export const authAPI = {

  // User login using /users/login endpoint
  login: async (username, password) => {
    try {
      const response = await api.post(
        "/users/login",
        new URLSearchParams({ username, password }),
        {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          withCredentials: true,
        }
      );
      return { 
        success: true, 
        data: response.data 
      };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.error || "Login failed" 
      };
    }
  },

  // Store login using /stores/login endpoint
  loginStore: async (username, password) => {
    try {
      const response = await api.post(
        "/stores/login",
        new URLSearchParams({ username, password }),
        {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          withCredentials: true,
        }
      );
      return { 
        success: true, 
        data: response.data 
      };
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.error || "Store login failed" 
      };
    }
  },
};
