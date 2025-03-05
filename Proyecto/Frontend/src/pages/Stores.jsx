import { useEffect, useState } from "react";
import { getRestaurants } from "../services/api";
import { Link } from "react-router-dom";
import { Container, Row, Col, Card, Button } from "react-bootstrap";
import SearchBar from "../components/SearchBar";

function Stores() {
  const [stores, setStores] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    getRestaurants()
      .then((data) => {
        console.log("Restaurantes cargados:", data);
        setStores(data);
      })
      .catch((error) => setError(error.message))
      .finally(() => setLoading(false));
  }, []);

  // Filtrar restaurantes en tiempo real según la búsqueda
  const filteredStores = searchTerm
    ? stores.filter((store) =>
        store.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
        store.address.toLowerCase().includes(searchTerm.toLowerCase())
      )
    : stores;

  if (loading) return <p className="text-center mt-4">Cargando restaurantes...</p>;
  if (error) return <p className="text-danger text-center mt-4">Error: {error}</p>;

  return (
    <Container>

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
                  <Button variant="primary" as={Link} to={`/store/${store.id}`}>
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
