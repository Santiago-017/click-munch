import api from "./axiosInstance";

const API_URL = "http://localhost:8080/api";

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
}

// Crear una orden
export const createOrder = async (orderData) => {
  try {
      const response = await axios.post(`${API_URL}/orders/new-order`, orderData);
      return response.data;
  } catch (error) {
      console.error("Error en la solicitud:", error.response ? error.response.data : error.message);
      throw new Error("Error al crear la orden: " + (error.response ? error.response.data.message : error.message));
  }
};

// Obtener todas las órdenes
export const getAllOrders = async () => {
  try {
      const response = await axios.get(`${API_URL}/orders`);
      return response.data;
  } catch (error) {
      throw new Error("Error al obtener las órdenes: " + error.message);
  }
};
