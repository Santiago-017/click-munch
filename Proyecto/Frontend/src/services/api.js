// src/services/api.js
import axios from "axios";

const API_URL = "http://localhost:8080/api"; // URL base del backend

// Obtener todos los restaurantes
export const getRestaurants = async () => {
  try {
    const response = await axios.get(`${API_URL}/stores`);
    return response.data; // Devuelve la lista de restaurantes
  } catch (error) {
    throw new Error("Error al obtener los restaurantes: " + error.message);
  }
};

// Obtener el detalle de un restaurante por ID
export const getRestaurantById = async (id) => {
  try {
    const response = await axios.get(`${API_URL}/stores/${id}`);
    return response.data; // Devuelve los detalles del restaurante, incluyendo platos, bebidas y postres
  } catch (error) {
    throw new Error("Error al obtener el restaurante: " + error.message);
  }
};
