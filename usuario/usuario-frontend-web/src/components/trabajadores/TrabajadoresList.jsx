// src/components/trabajadores/TrabajadoresList.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLocationDot } from '@fortawesome/free-solid-svg-icons';


const TrabajadoresList = ({ trabajadores }) => {
  return (
    <>
      <div className="td-grid">
        {trabajadores.map((t) => (
          <div key={t.userId} className="td-card">
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
            <div className="td-card-actions">
              <Link to={`/perfil/${t.userId}`}>
                <button className="td-btn td-btn-profile">Perfil</button>
              </Link>
              <button className="td-btn td-btn-message">Mensaje</button>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default TrabajadoresList;
