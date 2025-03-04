import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getRestaurantById } from "../services/api";
import { Container, Row, Col, Card } from "react-bootstrap";

function StoreMenu() {
    const { id } = useParams();
    const [menu, setMenu] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        setLoading(true);
        setError(null);
        getRestaurantById(id)
            .then((data) => {
                console.log("Menú cargado:", data); // Depuración
                setMenu(data);
            })
            .catch((error) => setError(error.message))
            .finally(() => setLoading(false));
    }, [id]);

    if (loading) return <p className="text-center mt-4">Cargando menú...</p>;
    if (error) return <p className="text-danger text-center mt-4">Error: {error}</p>;

    return (
        <Container className="mt-4">
            <h2 className="text-center my-3 text-primary">Menú del Restaurante</h2>
            {menu && (
                <>
                    {/* Sección de Platos */}
                    <h4 className="text-center mt-4 mb-2 text-secondary border-bottom pb-1">Platos</h4>
                    <Row className="justify-content-center">
                        {menu.plates.length > 0 ? (
                            menu.plates.map((plate) => (
                                <Col key={plate.id} lg={3} md={4} sm={6} xs={12} className="mb-3">
                                    <Card className="shadow-sm border rounded">
                                        <Card.Img
                                            variant="top"
                                            src={`/images/${plate.image}`}
                                            alt={plate.name}
                                            className="rounded-top"
                                            style={{ height: "150px", objectFit: "cover" }}
                                        />
                                        <Card.Body className="text-center p-2">
                                            <Card.Title className="fw-semibold fs-6">{plate.name}</Card.Title>
                                            <Card.Text className="text-success fw-bold fs-6">Precio: ${plate.price}</Card.Text>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            ))
                        ) : (
                            <p className="text-center">No hay platos disponibles</p>
                        )}
                    </Row>

                    {/* Sección de Postres */}
                    <h4 className="text-center mt-4 mb-2 text-secondary border-bottom pb-1">Postres</h4>
                    <Row className="justify-content-center">
                        {menu.desserts.length > 0 ? (
                            menu.desserts.map((dessert) => (
                                <Col key={dessert.id} lg={3} md={4} sm={6} xs={12} className="mb-3">
                                    <Card className="shadow-sm border rounded">
                                        <Card.Img
                                            variant="top"
                                            src={`/images/${dessert.image}`}
                                            alt={dessert.name}
                                            className="rounded-top"
                                            style={{ height: "150px", objectFit: "cover" }}
                                        />
                                        <Card.Body className="text-center p-2">
                                            <Card.Title className="fw-semibold fs-6">{dessert.name}</Card.Title>
                                            <Card.Text className="text-success fw-bold fs-6">Precio: ${dessert.price}</Card.Text>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            ))
                        ) : (
                            <p className="text-center">No hay postres disponibles</p>
                        )}
                    </Row>

                    {/* Sección de Bebidas */}
                    <h4 className="text-center mt-4 mb-2 text-secondary border-bottom pb-1">Bebidas</h4>
                    <Row className="justify-content-center">
                        {menu.drinks.length > 0 ? (
                            menu.drinks.map((drink) => (
                                <Col key={drink.id} lg={3} md={4} sm={6} xs={12} className="mb-3">
                                    <Card className="shadow-sm border rounded">
                                        <Card.Img
                                            variant="top"
                                            src={`/images/${drink.image}`}
                                            alt={drink.name}
                                            className="rounded-top"
                                            style={{ height: "150px", objectFit: "cover" }}
                                        />
                                        <Card.Body className="text-center p-2">
                                            <Card.Title className="fw-semibold fs-6">{drink.name}</Card.Title>
                                            <Card.Text className="text-success fw-bold fs-6">Precio: ${drink.price}</Card.Text>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            ))
                        ) : (
                            <p className="text-center">No hay bebidas disponibles</p>
                        )}
                    </Row>
                </>
            )}
        </Container>
    );
}

export default StoreMenu;
