// src/pages/StoreDetail.jsx
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getRestaurantById } from "../services/api";

function StoreDetail() {
  const { id } = useParams();
  const [restaurant, setRestaurant] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);

    getRestaurantById(id)
      .then((data) => {
        setRestaurant(data);
      })
      .catch((error) => {
        setError(error.message);
      })
      .finally(() => {
        setLoading(false);
      });
  }, [id]);

  if (loading) return <p>Cargando detalles del restaurante...</p>;
  if (error) return <p style={{ color: "red" }}>Error: {error}</p>;
  if (!restaurant) return <p>No se encontró el restaurante.</p>;

  return (
    <div>
      <h1>{restaurant.name}</h1>
      <p>{restaurant.address}</p>

      <h3>Platos</h3>
      <ul>
        {restaurant.plates.map((plate) => (
          <li key={plate.id}>
            {plate.name} - ${plate.price}
          </li>
        ))}
      </ul>

      <h3>Bebidas</h3>
      <ul>
        {restaurant.drinks.map((drink) => (
          <li key={drink.id}>
            {drink.name} - ${drink.price}
          </li>
        ))}
      </ul>

      <h3>Postres</h3>
      <ul>
        {restaurant.desserts.map((dessert) => (
          <li key={dessert.id}>
            {dessert.name} - ${dessert.price}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default StoreDetail;
