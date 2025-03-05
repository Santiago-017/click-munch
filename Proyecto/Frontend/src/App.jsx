import { Routes, Route } from "react-router-dom";
import Stores from "./pages/stores";
import StoreMenu from "./pages/StoreMenu";
import NavigationBar from "./components/NavigationBar";
import "./App.css";


function App() {
  return (
    <div>
      <NavigationBar />
    <Routes>
      <Route path="/" element={<Stores />} />
      <Route path="/store/:id" element={<StoreMenu />} />
    </Routes>
    </div>
  );
}

export default App;
