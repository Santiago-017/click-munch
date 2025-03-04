import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Stores from './components/stores';
import StoreDetail from './components/storeDetail';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/stores" element={<Stores />} />
        <Route path="/stores/:id" element={<StoreDetail />} />
      </Routes>
    </Router>
  );
}

export default App;