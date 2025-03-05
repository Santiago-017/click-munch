// App.jsx
import Stores from "./pages/stores";
import StoreMenu from "./pages/StoreMenu";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import NavigationBar from "./components/NavigationBar";
import LoginUser from "./pages/LoginUser";
import LoginStore from "./pages/LoginStore";
import { CartProvider } from "./context/CartContext.jsx";
import "./App.css";

import Orders from "./pages/Orders.jsx";
import Register from "./pages/RegisterUser.jsx";

function App() {
  return (
    <CartProvider>
        <NavigationBar />
        <Routes>
            <Route path="/orders" element={<Orders />} />
            <Route path="/loginUser" element={<LoginUser />} />
            <Route path="/registerUser" element={<Register />} />
            <Route path="/loginStore" element={<LoginStore />} />
            <Route path="/" element={<Stores />} />
            <Route path="/store/:id" element={<StoreMenu />} />
        </Routes>
    </CartProvider>
  );
}

export default App;
