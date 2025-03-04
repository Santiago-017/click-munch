import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

const StoreDetail = () => {
  const { id } = useParams();
  const [store, setStore] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeTab, setActiveTab] = useState('plates');

  useEffect(() => {
    const fetchStore = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/stores/${id}`);
        if (!response.ok) throw new Error('Store not found');
        const data = await response.json();
        setStore(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    
    fetchStore();
  }, [id]);

  if (loading) return (
    <div className="d-flex justify-content-center mt-5">
      <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  );

  if (error) return (
    <div className="container mt-4">
      <div className="alert alert-danger">
        Error: {error}
        <Link to="/" className="btn btn-link mt-2">
          Back to Stores
        </Link>
      </div>
    </div>
  );

  return (
    <div className="container mt-4">
      <Link to="/stores" className="btn btn-outline-primary mb-4">
        ← Back to Stores
      </Link>
      
      <div className="card shadow">
        <div className="card-header bg-primary text-white">
          <h2 className="mb-0">{store.name}</h2>
          <small>{store.alias}</small>
        </div>
        
        <div className="card-body">
          <p className="text-muted">
            <i className="bi bi-geo-alt me-2"></i>
            {store.address}
          </p>

          {/* Tabs */}
          <nav>
            <div className="nav nav-tabs mb-4">
              <button 
                className={`nav-link ${activeTab === 'plates' ? 'active' : ''}`}
                onClick={() => setActiveTab('plates')}
              >
                Platos ({store.plates.length})
              </button>
              <button 
                className={`nav-link ${activeTab === 'drinks' ? 'active' : ''}`}
                onClick={() => setActiveTab('drinks')}
              >
                Bebidas ({store.drinks.length})
              </button>
              <button 
                className={`nav-link ${activeTab === 'desserts' ? 'active' : ''}`}
                onClick={() => setActiveTab('desserts')}
              >
                Postres ({store.desserts.length})
              </button>
            </div>
          </nav>

          {/* Tab Content */}
          <div className="row g-4">
            {activeTab === 'plates' && store.plates.map(plate => (
              <div key={plate.id} className="col-md-6 col-lg-4">
                <div className="card h-100 shadow-sm">
                  <img src={plate.image} className="card-img-top" alt={plate.name} 
                       style={{ height: '200px', objectFit: 'cover' }} />
                  <div className="card-body">
                    <h5 className="card-title">{plate.name}</h5>
                    <p className="card-text">{plate.description}</p>
                    <p className="text-success fw-bold">${plate.price.toLocaleString()}</p>
                  </div>
                </div>
              </div>
            ))}

            {activeTab === 'drinks' && store.drinks.map(drink => (
              <div key={drink.id} className="col-md-6 col-lg-4">
                <div className="card h-100 shadow-sm">
                  <img src={drink.image} className="card-img-top" alt={drink.name} 
                       style={{ height: '200px', objectFit: 'cover' }} />
                  <div className="card-body">
                    <h5 className="card-title">{drink.name}</h5>
                    <p className="text-success fw-bold">${drink.price.toLocaleString()}</p>
                  </div>
                </div>
              </div>
            ))}

            {activeTab === 'desserts' && store.desserts.map(dessert => (
              <div key={dessert.id} className="col-md-6 col-lg-4">
                <div className="card h-100 shadow-sm">
                  <img src={dessert.image} className="card-img-top" alt={dessert.name} 
                       style={{ height: '200px', objectFit: 'cover' }} />
                  <div className="card-body">
                    <h5 className="card-title">{dessert.name}</h5>
                    <p className="text-success fw-bold">${dessert.price.toLocaleString()}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default StoreDetail;