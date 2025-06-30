// src/components/FAQSection.jsx
import React, { useState } from 'react';
import '../css/FAQSection.css';

const faqs = [
  {
    pregunta: '¿Cómo puedo registrarme?',
    respuesta: 'Haz clic en "Registrarse" y sigue las instrucciones.',
  },
  {
    pregunta: '¿Qué tipo de servicios puedo encontrar en KaiMaki?',
    respuesta: 'Limpieza, reparaciones, jardinería, cuidado de mascotas, entre otros.',
  },
  {
    pregunta: '¿Cómo contacto con un trabajador?',
    respuesta: 'Una vez seleccionado, puedes comunicarte directamente fuera de la plataforma.',
  },
  {
    pregunta: '¿Cómo sé si los trabajadores son confiables?',
    respuesta: 'Todos son verificados. Además, puedes revisar calificaciones y comentarios.',
  },
  {
    pregunta: '¿Cuánto cuesta utilizar KaiMaki?',
    respuesta: 'La plataforma es gratuita para clientes. Solo pagas al trabajador.',
  },
  {
    pregunta: '¿Cómo puedo convertirme en trabajador de KaiMaki?',
    respuesta: 'Regístrate y espera la verificación de tu perfil antes de aparecer en el sistema.',
  },
];

const FAQSection = () => {
  const [openIndex, setOpenIndex] = useState(null);

  const toggleFAQ = (index) => {
    setOpenIndex(index === openIndex ? null : index);
  };

  return (
    <section className="faq-section">
      <h2 className="faq-title">Preguntas Frecuentes</h2>
      <div className="faq-list">
        {faqs.map((faq, index) => (
          <div className="faq-item" key={index}>
            <button
              className={`faq-question ${openIndex === index ? 'open' : ''}`}
              onClick={() => toggleFAQ(index)}
            >
              {faq.pregunta}
              <span className="faq-icon">{openIndex === index ? '−' : '+'}</span>
            </button>
            <div className={`faq-answer ${openIndex === index ? 'visible' : ''}`}>
              {faq.respuesta}
            </div>
          </div>
        ))}
      </div>
    </section>
  );
};

export default FAQSection;
