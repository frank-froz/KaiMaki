// src/components/trabajadores/TrabajadoresList.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLocationDot } from '@fortawesome/free-solid-svg-icons';


const TrabajadoresList = ({ trabajadores }) => {
  const [popupTrabajador, setPopupTrabajador] = useState(null);
  const navigate = useNavigate();

  const handleCardClick = (t, e) => {
    // Evitar que el popup se cierre si se hace clic dentro de la tarjeta
    e.stopPropagation();
    setPopupTrabajador(t);
  };

  const closePopup = () => setPopupTrabajador(null);

  return (
    <>
      <div className="td-grid" onClick={closePopup}>
        {trabajadores.map((t) => (
          <div
            key={t.userId}
            className={`td-card${popupTrabajador && popupTrabajador.userId === t.userId ? ' td-card-selected' : ''}`}
            onClick={(e) => handleCardClick(t, e)}
            style={{cursor:'pointer'}}
          >
            <img
              src={t.fotoPerfil || "/avatar.png"}
              alt="Foto"
              className="td-avatar"
            />
            <div className="td-card-info">
              <div className="td-card-name">{t.nombreCompleto}</div>
              <div className="td-card-label">Especialidad</div>
              <div className="td-card-specialidades">
                {t.oficios?.length > 0 ? t.oficios.join(', ') : 'No registradas'}
              </div>
              <div className="td-card-location">
                <FontAwesomeIcon icon={faLocationDot} style={{ marginRight: '8px' }} />
                {[t.departamento, t.provincia, t.distrito]
                  .map(item => item || 'No especificada')
                  .join(' | ')}
              </div>
            </div>
            <div className="td-card-actions"></div>
          </div>
        ))}
      </div>
      {popupTrabajador && (
        <div className="td-popup-overlay" onClick={closePopup}>
          <div className="td-popup-card" onClick={e => e.stopPropagation()}>
            <button className="td-popup-close" onClick={closePopup}>&times;</button>
            <img src={popupTrabajador.fotoPerfil || '/avatar.png'} alt="Foto" className="td-popup-avatar" />
            <div className="td-popup-nombre">{popupTrabajador.nombreCompleto}</div>
            <div className="td-popup-presentacion">{popupTrabajador.presentacion || 'Sin presentación.'}</div>
            <button className="td-popup-perfil-btn" onClick={() => window.location.href = `/perfil/${popupTrabajador.userId}`}>
              Visitar perfil
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default TrabajadoresList;
