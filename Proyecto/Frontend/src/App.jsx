import { Routes, Route } from "react-router-dom";
import Stores from "./pages/stores";
import StoreMenu from "./pages/StoreMenu";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Stores />} />
      <Route path="/store/:id" element={<StoreMenu />} />
    </Routes>
  );
}

export default App;
