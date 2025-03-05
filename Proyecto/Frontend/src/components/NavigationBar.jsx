import { Navbar, Nav, Container, Button, Offcanvas } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useState } from "react";
import { FaBars, FaShoppingCart } from "react-icons/fa";
import { useCart } from "../context/CartContext.jsx";
import CartOffcanvas from "./CartOffcanvas";


function NavigationBar() {
    const [showMenu, setShowMenu] = useState(false);
    const [showCart, setShowCart] = useState(false);
    const { cart } = useCart(); // Accede al carrito desde el contexto
    const navigate = useNavigate(); // Hook para la navegación

    const handleCloseMenu = () => setShowMenu(false);
    const handleCloseCart = () => setShowCart(false);

  const handleNavigateHome = () => {
    navigate("/");
    handleCloseMenu();
  };

  // Handle logout by calling the Spring Boot /logout endpoint
  const handleLogout = async () => {
    try {
      await axios.post(
        "http://localhost:8080/logout",
        {},
        { withCredentials: true }
      );
      // Clear any client-side auth state
      localStorage.removeItem("isLoggedIn");
      setIsLoggedIn(false);
      navigate("/login");
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

    return (
        <>
            {/* Navbar principal */}
            <Navbar expand="lg" className="custom-navbar">
                <Container className="d-flex align-items-center justify-content-between">
    
                    {/* Menú desplegable */}
                    <button className="menu-toggle-btn" onClick={() => setShowMenu(!showMenu)}>
                        <FaBars size={24} />
                    </button>

                    {/* Título */}
                    <Navbar.Brand as={Link} to="/" className="navbar-title text-center mx-auto" onClick={handleNavigateHome}>
                        CLICK MUNCH
                    </Navbar.Brand>

                    {/* Carrito de compras */}
                    <Button variant="light" className="cart-button ms-auto d-flex align-items-center" onClick={() => setShowCart(true)}>
                        <FaShoppingCart className="me-1" />
                        ({cart.length})
                    </Button>

                </Container>
            </Navbar>



            {/* Menú desplegable */}
            {showMenu && (
                <div className="menu-overlay" onClick={handleCloseMenu}>
                    <div className="menu" onClick={(e) => e.stopPropagation()}>
                        <button className="close-btn" onClick={handleCloseMenu}>X</button>
                        <Nav className="flex-column">
                            <Nav.Link className="menu-item" onClick={handleNavigateHome}>Inicio</Nav.Link>
                            <Nav.Link as={Link} to="/orders" className="menu-item" onClick={handleCloseMenu}>
                                Órdenes
                            </Nav.Link>


                            <Nav.Link as={Link} to="/contacto" className="menu-item" onClick={handleCloseMenu}>
                                Contáctenos
                            </Nav.Link>
                        </Nav>
                    </div>
                </div>
            )}

            {/* Offcanvas del carrito */}
            <CartOffcanvas show={showCart} handleClose={handleCloseCart} />
        </>
    );
}

export default NavigationBar;