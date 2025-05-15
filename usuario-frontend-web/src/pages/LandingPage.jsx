// src/pages/LandingPage.jsx
import React from 'react';
import { Link } from 'react-router-dom'; // Para enlaces de navegación

import '../css/LandingPage.css'; // Aquí se incluirán los estilos
import Header from '../components/Header';

const LandingPage = () => {
  return (
    <div className="landing-page">

      <Header />

      <main className="main-content">
        <h2>Bienvenido a Kaimaki</h2>
        <p>
          Encuentra profesionales confiables y capacitados para las tareas que necesitas. 
          Rápido, seguro y bajo demanda.
        </p>
        <Link to="/start" className="cta-button">Empezar</Link>
      </main>

      <section className="offices">
        <div className="card">
          <h3>Oficio 01</h3>
          <p>Descripción del oficio 01</p>
        </div>
        <div className="card">
          <h3>Oficio 02</h3>
          <p>Descripción del oficio 02</p>
        </div>
        <div className="card">
          <h3>Oficio 03</h3>
          <p>Descripción del oficio 03</p>
        </div>
        <div className="card">
          <h3>Oficio 04</h3>
          <p>Descripción del oficio 04</p>
        </div>
        <div className="card">
          <h3>Oficio 05</h3>
          <p>Descripción del oficio 05</p>
        </div>
      </section>
    </div>
  );
}

export default LandingPage;
