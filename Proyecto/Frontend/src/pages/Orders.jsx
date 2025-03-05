import { useEffect, useState } from "react";
import { getAllOrders } from "../services/api";
import { Container, Table, Spinner, Alert, Badge, Card } from "react-bootstrap";

function Orders() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        setLoading(true);
        setError(null);
        getAllOrders()
            .then((data) => {
                console.log("Órdenes recibidas:", data); // Depuración
                setOrders(data);
            })
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <Spinner animation="border" className="d-block mx-auto mt-4" />;
    if (error) return <Alert variant="danger" className="text-center mt-4">Error: {error}</Alert>;

    return (
        <Container className="mt-4">
            <h2 className="text-center text-primary">Órdenes Registradas</h2>
            {orders.length > 0 ? (
                <Table striped bordered hover className="mt-3">
                    <thead>
                        <tr>
                            <th>ID Orden</th>
                            <th>Usuario</th>
                            <th>Estado</th>
                            <th>Productos</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map((order) => (
                            <tr key={order.id}>
                                <td>{order.id}</td>
                                <td>{order.userId}</td>
                                <td>
                                    <Badge bg={order.status === "PENDING" ? "warning" : "success"}>
                                        {order.status}
                                    </Badge>
                                </td>
                                <td>
                                    <div className="d-flex flex-wrap">
                                        {/* Asegurar que siempre se rendericen productos */}
                                        {order.plates?.length > 0 ? (
                                            order.plates.map((plate) => (
                                                <Card key={plate.id} className="m-1 p-1 text-center" style={{ width: "80px" }}>
                                                    <Card.Img src={`/images/plates/${plate.plateId}.jpg`} alt={plate.name || "Plato"} className="img-fluid" />
                                                    <Card.Body className="p-1">
                                                        <small>{plate.name || "Plato"}</small>
                                                        <br />
                                                        <small><strong>x{plate.quantity || 1}</strong></small>
                                                    </Card.Body>
                                                </Card>
                                            ))
                                        ) : (
                                            <small className="text-muted">Sin platos</small>
                                        )}

                                        {order.drinks?.length > 0 ? (
                                            order.drinks.map((drink) => (
                                                <Card key={drink.id} className="m-1 p-1 text-center" style={{ width: "80px" }}>
                                                    <Card.Img src={`/images/drinks/${drink.drinkId}.jpg`} alt={drink.name || "Bebida"} className="img-fluid" />
                                                    <Card.Body className="p-1">
                                                        <small>{drink.name || "Bebida"}</small>
                                                        <br />
                                                        <small><strong>x{drink.quantity || 1}</strong></small>
                                                    </Card.Body>
                                                </Card>
                                            ))
                                        ) : (
                                            <small className="text-muted">Sin bebidas</small>
                                        )}

                                        {order.desserts?.length > 0 ? (
                                            order.desserts.map((dessert) => (
                                                <Card key={dessert.id} className="m-1 p-1 text-center" style={{ width: "80px" }}>
                                                    <Card.Img src={`/images/desserts/${dessert.dessertId}.jpg`} alt={dessert.name || "Postre"} className="img-fluid" />
                                                    <Card.Body className="p-1">
                                                        <small>{dessert.name || "Postre"}</small>
                                                        <br />
                                                        <small><strong>x{dessert.quantity || 1}</strong></small>
                                                    </Card.Body>
                                                </Card>
                                            ))
                                        ) : (
                                            <small className="text-muted">Sin postres</small>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            ) : (
                <p className="text-center mt-3">No hay órdenes registradas.</p>
            )}
        </Container>
    );
}

export default Orders;
