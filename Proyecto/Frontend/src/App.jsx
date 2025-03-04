// src/App.jsx
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Stores from "./pages/Stores";
import StoreDetail from "./pages/StoreDetail";
import SearchBar from "./components/SearchBar"; 

function App() {
  return (
    <Routes>
      
      <Route path="/" element={<Stores />} />
      <Route path="/restaurant/:id" element={<StoreDetail />} />
    </Routes>
  );
}

export default App;
