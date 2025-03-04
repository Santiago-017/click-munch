import { useEffect, useState } from "react";
import { getRestaurants } from "../services/api";
import { Link } from "react-router-dom";
import { Container, Row, Col, Card, Button } from "react-bootstrap";
import SearchBar from "../components/SearchBar"; // Importamos el nuevo componente

function Stores() {
  const [stores, setStores] = useState([]);
  const [searchTerm, setSearchTerm] = useState(""); // Estado para la búsqueda
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

  // Filtrar restaurantes por nombre o dirección en tiempo real
  const filteredStores = stores.filter((store) =>
    store.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    store.address.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) return <p className="text-center mt-4">Cargando restaurantes...</p>;
  if (error) return <p className="text-danger text-center mt-4">Error: {error}</p>;

  return (
    <Container>
      <h1 className="text-center my-4">Restaurantes</h1>

      {/* Barra de búsqueda */}
      <SearchBar searchTerm={searchTerm} setSearchTerm={setSearchTerm} />

      <Row>
        {filteredStores.length > 0 ? (
          filteredStores.map((store) => (
            <Col key={store.id} md={4} sm={6} xs={12} className="mb-4">
              <Card className="shadow">
                <Card.Body>
                  <Card.Title>{store.name}</Card.Title>
                  <Card.Text>{store.address}</Card.Text>
                  <Button variant="primary" as={Link} to={`/restaurant/${store.id}`}>
                    Ver menú
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          ))
        ) : (
          <p className="text-center mt-4">No se encontraron restaurantes que coincidan con la búsqueda.</p>
        )}
      </Row>
    </Container>
  );
}

export default Stores;
