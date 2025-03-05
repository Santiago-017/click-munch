import { Navbar, Nav, Container } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { FaBars } from "react-icons/fa";
import axios from "axios";

function NavigationBar() {
  const [showMenu, setShowMenu] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const navigate = useNavigate();

  // Check auth state when the component mounts
  useEffect(() => {
    // Here you might check a cookie, token, or make an API call.
    // For this example, we check localStorage.
    const loggedInFlag = localStorage.getItem("isLoggedIn");
    setIsLoggedIn(loggedInFlag === "true");
  }, []);

  const handleCloseMenu = () => setShowMenu(false);

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
      <Navbar expand="lg" className="custom-navbar">
        <Container>
          <button className="menu-toggle-btn" onClick={() => setShowMenu(!showMenu)}>
            <FaBars size={24} />
          </button>
          <Navbar.Brand as={Link} to="/" className="navbar-title" onClick={handleNavigateHome}>
            CLICK MUNCH
          </Navbar.Brand>
          {/* Only display Logout if the user is logged in */}
          {isLoggedIn && (
            <Nav className="ms-auto">
              <Nav.Link onClick={handleLogout}>Logout</Nav.Link>
            </Nav>
          )}
        </Container>
      </Navbar>

      {showMenu && (
        <div className="menu-overlay" onClick={handleCloseMenu}>
          <div className="menu" onClick={(e) => e.stopPropagation()}>
            <button className="close-btn" onClick={handleCloseMenu}>X</button>
            <Nav className="flex-column">
              <Nav.Link className="menu-item" onClick={handleNavigateHome}>Inicio</Nav.Link>
              <Nav.Link as={Link} to="/contacto" className="menu-item" onClick={handleCloseMenu}>
                Contáctenos
              </Nav.Link>
              {isLoggedIn && (
                <Nav.Link className="menu-item" onClick={handleLogout}>
                  Logout
                </Nav.Link>
              )}
            </Nav>
          </div>
        </div>
      )}
    </>
  );
}

export default NavigationBar;