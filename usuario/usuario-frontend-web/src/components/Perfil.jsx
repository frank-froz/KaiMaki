import React, { useState } from 'react';
import '../css/Perfil.css';

const Perfil = ({ data, editable = false, onSave }) => {
  const [modoEdicion, setModoEdicion] = useState(false);
  const [perfil, setPerfil] = useState(data);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPerfil(prev => ({ ...prev, [name]: value }));
  };

  const handleGuardar = () => {
    if (onSave) onSave(perfil);
    setModoEdicion(false);
  };

  return (
    
    <div className="perfil-card">
      <div className="perfil-header">
        <h2>Perfil de Usuario</h2>
        {editable && (
          <button className="perfil-editar" onClick={() => setModoEdicion(!modoEdicion)}>
            {modoEdicion ? 'Cancelar' : 'Editar'}
          </button>
        )}
      </div>

      <div className="perfil-foto">
        <img
          src={perfil.fotoPerfil || '/placeholder.jpg'}
          alt="Foto de perfil"
        />
      </div>

      <div className="perfil-datos">
        {[
          { label: 'Nombres', key: 'nombre' },
          { label: 'Apellidos', key: 'apellido' },
          { label: 'Correo', key: 'correo' },
          { label: 'Presentación', key: 'presentacion' }
        ].map(({ label, key }) => (
          <div className="perfil-campo" key={key}>
            <label>{label}:</label>
            {modoEdicion ? (
              <input type="text" name={key} value={perfil[key] || ''} onChange={handleChange} />
            ) : (
              <p>{perfil[key] || '—'}</p>
            )}
          </div>
        ))}

        <div className="perfil-campo">
          <label>Ubicación:</label>
          <p>{perfil.distrito}, {perfil.provincia}, {perfil.departamento}</p>
        </div>
      </div>

      {perfil.oficios?.length > 0 && (
        <div className="perfil-listado">
          <h3>Oficios:</h3>
          <ul>
            {perfil.oficios.map((o, i) => <li key={i}>{o}</li>)}
          </ul>
        </div>
      )}

      {perfil.calificaciones?.length > 0 && (
        <div className="perfil-listado">
          <h3>Calificaciones:</h3>
          {perfil.calificaciones.map((cal, i) => (
            <div key={i} className="perfil-calificacion">
              <p className="perfil-calif-fecha">{cal.fecha}</p>
              <p>⭐ {cal.puntuacion} - {cal.comentario}</p>
            </div>
          ))}
        </div>
      )}

      {modoEdicion && (
        <button className="perfil-guardar" onClick={handleGuardar}>
          Guardar Cambios
        </button>
      )}
    </div>
  );
};

export default Perfil;
