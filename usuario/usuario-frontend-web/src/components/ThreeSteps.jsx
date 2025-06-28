// src/components/ThreeSteps.jsx
import React from 'react';
import '../css/ThreeSteps.css';
import imagenHombre from '../assets/hombreseñalando.png'; // Ajusta la ruta si es necesario

const ThreeSteps = () => {
  return (
    <section id="todo-en-3-pasos" className="three-steps-container">
      <div className="three-steps-wrapper">
        <div className="three-steps-card">
          <h2 className="three-steps-title">TODO EN 3 PASOS</h2>
          <div className="three-steps-content">
            
            {/* Imagen (visible solo en pantallas grandes) */}
            <div className="three-steps-image">
              <img src={imagenHombre} alt="Imagen ilustrativa" />
            </div>

            {/* Lista de pasos */}
            <div className="three-steps-list">
              {[
                "Crea tu perfil como cliente.",
                "Busca el servicio que necesitas y elige al mejor profesional.",
                "El profesional realiza el trabajo y luego puedes calificarlo.",
              ].map((texto, index) => (
                <div className="step-item" key={index}>
                  <div className="step-number">{index + 1}</div>
                  <p className="step-text">{texto}</p>
                </div>
              ))}
            </div>
            
          </div>
        </div>
      </div>
    </section>
  );
};

export default ThreeSteps;
