import React, { useEffect, useState } from 'react';
import '../../styles/components/Perfil.css';

const Perfil = ({ data, editable = false, onSave, onMessageClick }) => {
  const [modoEdicion, setModoEdicion] = useState(false);
  const [perfil, setPerfil] = useState(data);
  const [saving, setSaving] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPerfil(prev => ({ ...prev, [name]: value }));
  };

  const handleGuardar = async () => {
    setSaving(true);
    if (onSave) await onSave(perfil);
    setModoEdicion(false);
    setSaving(false);
  };

  useEffect(() => {
    setPerfil(data);
    setModoEdicion(false);
  }, [data]);

  const ubicacion = [perfil.distrito, perfil.provincia, perfil.departamento].filter(Boolean).join(', ');

  return (
    <div className="perfil-fb-card">
      <div className="perfil-fb-banner"></div>
      <div className="perfil-fb-header-row">
        <div className="perfil-fb-foto-box">
          <img className="perfil-fb-foto" src={perfil.fotoPerfil || '/avatar.png'} alt="Foto de perfil" />
        </div>
        <div className="perfil-fb-nombre-botones-row">
          <div className="perfil-fb-nombre">
            {modoEdicion ? (
              <>
                <input
                  type="text"
                  name="nombre"
                  value={perfil.nombre || ''}
                  onChange={handleChange}
                  placeholder="Nombre"
                  className="perfil-fb-input-nombre"
                  style={{marginRight: 8, minWidth: 90}}
                />
                <input
                  type="text"
                  name="apellido"
                  value={perfil.apellido || ''}
                  onChange={handleChange}
                  placeholder="Apellido"
                  className="perfil-fb-input-apellido"
                  style={{minWidth: 90}}
                />
              </>
            ) : (
              <>{perfil.nombre} {perfil.apellido}</>
            )}
          </div>
          {editable && (
            <button className="perfil-fb-editar" onClick={() => setModoEdicion(!modoEdicion)}>
              {modoEdicion ? 'Cancelar' : 'Editar perfil'}
            </button>
          )}
          {!editable && (
            <button className="perfil-fb-mensaje" onClick={() => onMessageClick && onMessageClick(perfil)}>
              Enviar mensaje
            </button>
          )}
        </div>
      </div>
      <div className="perfil-fb-body">
        <div className="perfil-fb-col-izq">
          <div className="perfil-fb-bloque-presentacion">
            <h3>Presentación</h3>
            <div className="perfil-fb-presentacion-texto">
              {modoEdicion ? (
                <textarea name="presentacion" value={perfil.presentacion || ''} onChange={handleChange} placeholder="Presentación" />
              ) : (
                <span>{perfil.presentacion || '—'}</span>
              )}
            </div>
            <hr className="perfil-fb-hr" />
            <div className="perfil-fb-ubicacion">
              <span className="perfil-fb-ubicacion-label">Ubicación:</span>
              {modoEdicion ? (
                <>
                  <input type="text" name="distrito" value={perfil.distrito || ''} onChange={handleChange} placeholder="Distrito" style={{marginRight:4}} />
                  <input type="text" name="provincia" value={perfil.provincia || ''} onChange={handleChange} placeholder="Provincia" style={{marginRight:4}} />
                  <input type="text" name="departamento" value={perfil.departamento || ''} onChange={handleChange} placeholder="Departamento" />
                </>
              ) : (
                <span className="perfil-fb-ubicacion-text">{ubicacion || '—'}</span>
              )}
            </div>
          </div>
        </div>
        {!editable && (
        <div className="perfil-fb-col-der">
          <div className="perfil-fb-bloque-instrucciones-card">
            <ul className="perfil-fb-instrucciones-list">
              <li>Revisa la información y experiencia del trabajador</li>
              <li>Ten en cuenta la ubicación, para llegar a un acuerdo con el trabajador.</li>
              <li>Contactalo dando clic en "Enviar mensaje"</li>
            </ul>
          </div>
        </div>
        )}
      </div>
      {modoEdicion && (
        <button className="perfil-fb-guardar" onClick={handleGuardar} disabled={saving}>
          {saving ? 'Guardando...' : 'Guardar Cambios'}
        </button>
      )}
    </div>
  );
};

export default Perfil;
