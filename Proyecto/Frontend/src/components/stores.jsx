import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Link } from 'react-router-dom';

const Stores = () => {
  const [stores, setStores] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeTabs, setActiveTabs] = useState({});

  // Initialize active tabs
  useEffect(() => {
    if (stores.length > 0) {
      const initialTabs = stores.reduce((acc, store) => {
        acc[store.id] = store.plates.length > 0 ? 'plates' : 
                        store.drinks.length > 0 ? 'drinks' : 
                        store.desserts.length > 0 ? 'desserts' : 'none';
        return acc;
      }, {});
      setActiveTabs(initialTabs);
    }
  }, [stores]);

  const handleTabClick = (storeId, tabName) => {
    setActiveTabs(prev => ({
      ...prev,
      [storeId]: tabName
    }));
  };

  useEffect(() => {
    const fetchStores = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/stores');
        if (!response.ok) throw new Error('Failed to fetch stores');
        const data = await response.json();
        setStores(data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchStores();
  }, []);

  if (loading) return (
    <div className="d-flex justify-content-center mt-5">
      <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  );

  if (error) return (
    <div className="alert alert-danger m-3" role="alert">
      Error: {error}
    </div>
  );

  return (
    <div className="container mt-3">
      <h1 className="text-center mb-3">Available Stores</h1>
      <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-3">
        {stores.map((store) => (
          <div key={store.id} className="col">
            <div className="card shadow-sm h-100">
            <Link to={`/stores/${store.id}`} className="text-primary text-decoration-none">
                <h5 className="mb-0">{store.name}</h5>
                    <small>{store.alias}</small>
            </Link>
              
              <div className="card-body p-2">
                <p className="text-muted small mb-3">
                  <i className="bi bi-geo-alt me-1"></i>
                  {store.address}
                </p>

                {/* Tab Navigation */}
                <nav>
                  <div className="nav nav-tabs mb-3" role="tablist">
                    <button
                      className={`nav-link ${activeTabs[store.id] === 'plates' ? 'active' : ''} ${store.plates.length === 0 ? 'disabled' : ''}`}
                      onClick={() => handleTabClick(store.id, 'plates')}
                      type="button"
                    >
                      Platos
                    </button>
                    <button
                      className={`nav-link ${activeTabs[store.id] === 'drinks' ? 'active' : ''} ${store.drinks.length === 0 ? 'disabled' : ''}`}
                      onClick={() => handleTabClick(store.id, 'drinks')}
                      type="button"
                    >
                      Bebidas
                    </button>
                    <button
                      className={`nav-link ${activeTabs[store.id] === 'desserts' ? 'active' : ''} ${store.desserts.length === 0 ? 'disabled' : ''}`}
                      onClick={() => handleTabClick(store.id, 'desserts')}
                      type="button"
                    >
                      Postres
                    </button>
                  </div>
                </nav>

                {/* Tab Content */}
                <div className="tab-content">
                  {/* Plates Tab */}
                  <div className={`tab-pane fade ${activeTabs[store.id] === 'plates' ? 'show active' : ''}`}>
                    <div className="row row-cols-1 g-2">
                      {store.plates.map((plate) => (
                        <div key={plate.id} className="col">
                          <div className="card border-0 shadow-sm">
                            <img 
                              src={plate.image} 
                              className="card-img-top" 
                              alt={plate.name} 
                              style={{ height: '120px', objectFit: 'cover' }} 
                            />
                            <div className="card-body p-2">
                              <div className="d-flex justify-content-between align-items-start">
                                <h6 className="mb-0 fs-6 fw-bold">{plate.name}</h6>
                                <span className="text-success fw-bold">
                                  ${plate.price.toLocaleString()}
                                </span>
                              </div>
                              {plate.description && (
                                <p className="small text-muted mb-0 mt-1">
                                  {plate.description}
                                </p>
                              )}
                            </div>
                          </div>
                        </div>
                      ))}
                      {store.plates.length === 0 && (
                        <div className="alert alert-warning mb-0 py-2">
                          No hay platos disponibles
                        </div>
                      )}
                    </div>
                  </div>

                  {/* Drinks Tab */}
                  <div className={`tab-pane fade ${activeTabs[store.id] === 'drinks' ? 'show active' : ''}`}>
                    <div className="row row-cols-2 g-2">
                      {store.drinks.map((drink) => (
                        <div key={drink.id} className="col">
                          <div className="card border-0 shadow-sm h-100">
                            <img 
                              src={drink.image} 
                              className="card-img-top" 
                              alt={drink.name} 
                              style={{ height: '80px', objectFit: 'cover' }} 
                            />
                            <div className="card-body p-2">
                              <div className="d-flex justify-content-between align-items-center">
                                <h6 className="mb-0 fs-6">{drink.name}</h6>
                                <span className="text-success fw-bold">
                                  ${drink.price.toLocaleString()}
                                </span>
                              </div>
                            </div>
                          </div>
                        </div>
                      ))}
                      {store.drinks.length === 0 && (
                        <div className="alert alert-warning mb-0 py-2">
                          No hay bebidas disponibles
                        </div>
                      )}
                    </div>
                  </div>

                  {/* Desserts Tab */}
                  <div className={`tab-pane fade ${activeTabs[store.id] === 'desserts' ? 'show active' : ''}`}>
                    <div className="row row-cols-2 g-2">
                      {store.desserts.map((dessert) => (
                        <div key={dessert.id} className="col">
                          <div className="card border-0 shadow-sm h-100">
                            <img 
                              src={dessert.image} 
                              className="card-img-top" 
                              alt={dessert.name} 
                              style={{ height: '80px', objectFit: 'cover' }} 
                            />
                            <div className="card-body p-2">
                              <div className="d-flex justify-content-between align-items-center">
                                <h6 className="mb-0 fs-6">{dessert.name}</h6>
                                <span className="text-success fw-bold">
                                  ${dessert.price.toLocaleString()}
                                </span>
                              </div>
                            </div>
                          </div>
                        </div>
                      ))}
                      {store.desserts.length === 0 && (
                        <div className="alert alert-warning mb-0 py-2">
                          No hay postres disponibles
                        </div>
                      )}
                    </div>
                  </div>
                </div>

                {/* Empty State for Entire Store */}
                {store.plates.length === 0 && 
                 store.drinks.length === 0 && 
                 store.desserts.length === 0 && (
                  <div className="alert alert-warning mb-0">
                    Menú no disponible
                  </div>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Stores;