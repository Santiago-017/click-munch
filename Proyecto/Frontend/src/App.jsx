import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import Stores from "./pages/Stores";
import StoreMenu from "./pages/StoreMenu";
import { CartProvider } from "./context/CartContext.jsx";
import "./App.css";
import Orders from "./pages/Orders.jsx";

function App() {
  return (
    <CartProvider>
        <NavigationBar />
        <Routes>
            <Route path="/orders" element={<Orders />} />
            <Route path="/" element={<Stores />} />
            <Route path="/store/:id" element={<StoreMenu />} />
        </Routes>
    </CartProvider>
  );
}

export default App;
