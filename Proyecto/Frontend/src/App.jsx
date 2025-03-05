// App.jsx
import { Routes, Route } from "react-router-dom";
import Stores from "./pages/stores";
import StoreMenu from "./pages/StoreMenu";
import NavigationBar from "./components/NavigationBar";
import "./App.css";
import LoginUser from "./pages/LoginUser";
import LoginStore from "./pages/LoginStore";

function App() {
  return (
    <div>
      <NavigationBar />
      <Routes>
        <Route path="/loginUser" element={<LoginUser />} />
        <Route path="/loginStore" element={<LoginStore />} />
        <Route path="/" element={<Stores />} />
        <Route path="/store/:id" element={<StoreMenu />} />
      </Routes>
    </div>
  );
}

export default App;
