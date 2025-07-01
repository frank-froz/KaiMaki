// src/pages/TrabajadoresPage.jsx
import React, { useEffect, useState } from 'react';
import Header from '../../components/layout/Header';
import TrabajadoresList from '../../components/trabajadores/TrabajadoresList.jsx';
import '../../styles/pages/TrabajadorPage.css';
import { getTrabajadoresDisponibles } from '../../services/trabajadorService.js';


const TrabajadoresPage = () => {
  const [trabajadores, setTrabajadores] = useState([]);
  const [busqueda, setBusqueda] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    getTrabajadoresDisponibles()
      .then(res => {
        setTrabajadores(res.data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Error al cargar trabajadores:", err);
        setError("No se pudieron cargar los trabajadores.");
        setLoading(false);
      });
  }, []);

  const trabajadoresFiltrados = trabajadores.filter(t =>
    t.oficios?.some(oficio =>
      oficio.toLowerCase().includes(busqueda.toLowerCase())
    )
  );

  return (
    <div className="td-main-container">
      <div className="header-main" style={{ position: 'fixed', top: 0, left: 0, right: 0, zIndex: 200 }}>
        <Header isLoggedIn={true} userName="Usuario" />
      </div>

      <section className="td-content" style={{ marginTop: '120px' }}>
        <div className="td-search-historial-row">
          <div className="td-searchbar-container">
            <input
              className="td-searchbar"
              type="text"
              placeholder="Búsqueda por especialidad"
              value={busqueda}
              onChange={(e) => setBusqueda(e.target.value)}
            />
          </div>
        </div>
          {loading ? (
            <p className="td-loading">Cargando trabajadores...</p>
          ) : error ? (
            <p className="td-error">{error}</p>
          ) : (
            <TrabajadoresList trabajadores={trabajadoresFiltrados} />
          )}
      </section>
    </div>
  );
};

export default TrabajadoresPage;
