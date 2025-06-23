// src/pages/TrabajadoresPage.jsx
import React, { useEffect, useState } from 'react';
import axios from '../services/api';
import Header from '../components/Header';
import TrabajadoresList from '../components/TrabajadoresList.jsx';
import '../css/TrabajadoresDisponibles.css';

const TrabajadoresPage = () => {
  const [trabajadores, setTrabajadores] = useState([]);
  const [busqueda, setBusqueda] = useState('');

  useEffect(() => {
    axios.get('/trabajadores/disponibles')
      .then(res => setTrabajadores(res.data))
      .catch(err => console.error("Error al cargar trabajadores:", err));
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

        <TrabajadoresList trabajadores={trabajadoresFiltrados} />
      </section>
    </div>
  );
};

export default TrabajadoresPage;
