import React from "react";
import { Offcanvas, Button, ListGroup, Image } from "react-bootstrap";
import { useCart } from "../context/CartContext";

function CartOffcanvas({ show, handleClose }) {
    const { cart, removeFromCart, clearCart, decreaseQuantity, addToCart } = useCart();

    return (
        <Offcanvas show={show} onHide={handleClose} placement="end" className="cart-offcanvas">

            <Offcanvas.Header closeButton>
                <Offcanvas.Title>Carrito de Compras</Offcanvas.Title>
            </Offcanvas.Header>
            <Offcanvas.Body>
                {cart.length === 0 ? (
                    <p className="text-center">El carrito está vacío.</p>
                ) : (
                    <>
                        <ListGroup>
                            {cart.map((item) => (
                                <ListGroup.Item key={item.id} className="d-flex align-items-center">
                                    <Image src={`/images/${item.image}`} alt={item.name} style={{ width: "50px", height: "50px", objectFit: "cover" }} rounded />
                                    <div className="ms-3">
                                        <h6>{item.name}</h6>
                                        <p className="mb-1">Precio: ${item.price}</p>
                                        <div className="d-flex align-items-center">
                                            <Button variant="outline-secondary" size="sm" onClick={() => decreaseQuantity(item.id)}>-</Button>
                                            <span className="mx-2">{item.quantity}</span>
                                            <Button variant="outline-secondary" size="sm" onClick={() => addToCart(item)}>+</Button>
                                        </div>
                                    </div>
                                    <Button variant="danger" size="sm" className="ms-auto" onClick={() => removeFromCart(item.id)}>X</Button>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                        <div className="mt-3 d-flex justify-content-between">
                            <Button variant="danger" onClick={clearCart}>Vaciar Carrito</Button>
                            <Button variant="success">Realizar Pedido</Button>
                        </div>
                    </>
                )}
            </Offcanvas.Body>
        </Offcanvas>
    );
}

export default CartOffcanvas;
