import { Navbar, Nav, Container } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { FaBars } from "react-icons/fa";

function NavigationBar() {
    const [showMenu, setShowMenu] = useState(false);
    const navigate = useNavigate(); // Hook para la navegación

    const handleCloseMenu = () => setShowMenu(false);

    // Función para manejar clic en "Inicio"
    const handleNavigateHome = () => {
        navigate("/"); // Navega a Inicio
        handleCloseMenu(); // Cierra el menú desplegable
    };

    return (
        <>
            {/* Navbar principal */}
            <Navbar expand="lg" className="custom-navbar">
                <Container>
                    <button className="menu-toggle-btn" onClick={() => setShowMenu(!showMenu)}>
                        <FaBars size={24} />
                    </button>
                    <Navbar.Brand as={Link} to="/" className="navbar-title" onClick={handleNavigateHome}>
                        CLICK MUNCH
                    </Navbar.Brand>
                </Container>
            </Navbar>

            {/* Menú desplegable */}
            {showMenu && (
                <div className="menu-overlay" onClick={handleCloseMenu}>
                    <div className="menu" onClick={(e) => e.stopPropagation()}>
                        <button className="close-btn" onClick={handleCloseMenu}>X</button>
                        <Nav className="flex-column">
                            <Nav.Link className="menu-item" onClick={handleNavigateHome}>Inicio</Nav.Link> {/* Aquí se aplica la función */}
                            <Nav.Link as={Link} to="/contacto" className="menu-item" onClick={handleCloseMenu}>
                                Contáctenos
                            </Nav.Link>
                        </Nav>
                    </div>
                </div>
            )}
        </>
    );
}

export default NavigationBar;
