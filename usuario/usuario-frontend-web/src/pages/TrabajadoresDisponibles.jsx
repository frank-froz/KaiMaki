import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import axios from '../services/api'
import '../css/TrabajadoresDisponibles.css'
import Header from '../components/Header'

const historialTags = [
  "engineering", "design", "ui/ux", "marketing", "management", "soft", "construction"
]

// Cambio: Componente UserProfile para mostrar avatar y logout en la barra superior
const UserProfile = () => {
  const navigate = useNavigate();
  const handleLogout = () => {
    navigate('/');
  };
  return (
    <div className="td-header-user-profile">
      <img src="/avatar.png" alt="User" className="td-header-avatar" />
      <span className="td-header-username">Usuario</span>
      <span className="td-header-logout" onClick={handleLogout}>log out</span>
    </div>
  );
};

const TrabajadoresDisponibles = () => {
  const [trabajadores, setTrabajadores] = useState([])

  useEffect(() => {
    axios.get('/trabajadores')
      .then(res => setTrabajadores(res.data))
      .catch(err => console.error(err))
  }, [])

  return (
    <div className="td-main-container">
      {/* Cambio: Header fijo y UserProfile en la barra superior */}
      <div className="header-main" style={{ position: 'fixed', top: 0, left: 0, right: 0, zIndex: 200 }}>
        <Header isLoggedIn={true} userName="Usuario" />
        <UserProfile />
      </div>
      <section className="td-content">
        {/* Cambio: Localización e Historial ahora están a la izquierda de la barra de búsqueda */}
        <div className="td-search-historial-row">
          <div className="td-historial-localizacion-box">
            <div className="td-sidebar-section">
              <div className="td-sidebar-title">Localización</div>
              <div className="td-location">
                <span className="td-location-icon">📍</span>
                <span className="td-location-text">Choose city</span>
                <span className="td-location-arrow">▼</span>
              </div>
            </div>
            <div className="td-sidebar-section">
              <div className="td-sidebar-title">Historial</div>
              <div className="td-tags">
                {historialTags.map(tag => (
                  <span className="td-tag" key={tag}>{tag}</span>
                ))}
              </div>
            </div>
          </div>
          <div className="td-searchbar-container">
            <input
              className="td-searchbar"
              type="text"
              placeholder="Búsqueda por especialidad"
            />
          </div>
        </div>
        <div className="td-grid">
          {trabajadores.map((t, index) => (
            <div key={index} className={`td-card ${index < 3 ? 'td-card-orange' : ''}`}>
              <img src={index < 3 ? "/avatar.png" : "/avatar2.png"} alt="Foto" className="td-avatar" />
              <div className="td-card-info">
                <div className="td-card-name">{t.nombre} {t.apellido}</div>
                <div className="td-card-label">Especialidad</div>
                <div className="td-card-specialidades">
                  {t.especialidades?.join(', ')}
                </div>
                <div className="td-card-label">Localización</div>
                <div className="td-card-location">
                  {t.ubicacion || 'No especificada'}
                </div>
              </div>
              <div className="td-card-actions">
                <button className="td-btn td-btn-profile">Perfil</button>
                <button className="td-btn td-btn-message">Mensaje</button>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  )
}

export default TrabajadoresDisponibles
