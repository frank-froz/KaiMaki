// src/pages/AboutPage.jsx
import React from 'react';
import '../../styles/pages/AboutPage.css';
import portada from '../../assets/about-us/portada.jpg';
import franklinImg from '../../assets/about-us/Frank.png';
import hectorImg from '../../assets/about-us/Han.png';
import kenedyImg from '../../assets/about-us/Kenedy.png';
import mision from '../../assets/about-us/mision.png';
import vision from '../../assets/about-us/vision.png';
import valor from '../../assets/about-us/valor.png';
import FAQSection from '../../components/home/FAQSection.JSX';
import Header from '../../components/layout/Header';

const AboutPage = () => {
  return (

    <div className="about-page">
            <Header />
      {/* Hero */}
      <div className="about-hero" style={{ backgroundImage: `url(${portada})` }}>
        <div className="overlay" />
        <div className="hero-content">
          <h1>Acerca de Nosotros</h1>
          <p>
            Descubre nuestra misión, visión y el equipo que trabaja para transformar tus ideas en soluciones.
            <br /> En KaiMaki, creemos que la vida puede ser más fácil cuando tienes el apoyo adecuado.
          </p>
        </div>
      </div>

      {/* Historia */}
      <section className="about-section">
        <h2>¿Cómo Inició?</h2>
        <p>
          KaiMaki nació de nuestra experiencia buscando soluciones confiables para el hogar,
          lo que nos inspiró a crear una plataforma accesible para todos. Pensamos en quienes necesitan ayuda
          y en quienes ofrecen su talento para resolver problemas diarios.
        </p>
      </section>

      {/* Misión, Visión, Valores */}
      <section className="about-grid-section">
        <div className="grid-card">
          <img src={mision} alt="Misión" />
          <h3>Nuestra Misión</h3>
          <p>
            Que las personas encuentren soluciones rápidas y seguras para sus necesidades del hogar,
            mientras brindamos oportunidades justas a los trabajadores.
          </p>
        </div>
        <div className="grid-card">
          <img src={vision} alt="Visión" />
          <h3>Nuestra Visión</h3>
          <p>
            Ser la plataforma líder en servicios a domicilio, destacándonos por la calidad e innovación.
          </p>
        </div>
        <div className="grid-card">
          <img src={valor} alt="Valores" />
          <h3>Nuestros Valores</h3>
          <p>
            Confianza, Eficiencia y Compromiso para brindarte la mejor experiencia.
          </p>
        </div>
      </section>

      {/* Equipo */}
      <section className="about-team">
        <h2>Nuestro Equipo</h2>
        <div className="team-grid">
          {[
            { nombre: 'Franklin Alvaro Huaytalla Rodriguez', rol: 'Scrum Master', img: franklinImg },
            { nombre: 'Hector Hanmer Castro Peñaloza', rol: 'Scrum Team', img: hectorImg },
            { nombre: 'Kenedy Ramos Huaman', rol: 'Scrum Team', img: kenedyImg },
          ].map((miembro, index) => (
            <div key={index} className="team-member">
              <img src={miembro.img} alt={miembro.nombre} />
              <h3>{miembro.nombre}</h3>
              <p>{miembro.rol}</p>
            </div>
          ))}
        </div>
        <FAQSection />
      </section>
    </div>
    
  );
};

export default AboutPage;
