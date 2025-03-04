import { useEffect, useState } from "react";
import { getRestaurants } from "../services/api";
import { Link } from "react-router-dom";

function Stores() {
  const [stores, setStores] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    getRestaurants()
      .then((data) => setStores(data))
      .catch((error) => setError(error.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Cargando restaurantes...</p>;
  if (error) return <p style={{ color: "red" }}>Error: {error}</p>;

  return (
    <div style={{ display: "flex", flexWrap: "wrap", gap: "20px" }}>
      {stores.map((store) => (
        <div key={store.id} style={{ border: "1px solid #ccc", padding: "10px", width: "250px" }}>
          <h3>{store.name}</h3>
          <p>{store.address}</p>
          <Link to={`/restaurant/${store.id}`}>Ver menú</Link>
        </div>
      ))}
    </div>
  );
}

export default Stores;
