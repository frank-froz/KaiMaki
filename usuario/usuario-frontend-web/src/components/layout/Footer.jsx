// Footer.jsx
import React from 'react';
import '../../styles/layout/Footer.css';
import logoFacebook from '../../assets/logoFacebook.png';
import logoTwitter from '../../assets/logoTwitter.png';
import logoInstagram from '../../assets/logoInstagram.png';

const Footer = () => {
  return (
    <footer className="footer-apple-style">
      <div className="footer-apple-container">
        <div className="footer-apple-col">
          <h4 className="footer-apple-title" style={{color:'#222',transition:'color 0.2s'}} onMouseOver={e=>e.currentTarget.style.color='#f97316'} onMouseOut={e=>e.currentTarget.style.color='#222'}>Kai Maki</h4>
          <ul className="footer-apple-list">
            <li className="footer-apple-item">Conectamos clientes con profesionales para servicios de calidad en el hogar.</li>
            <li className="footer-apple-item">Fácil, rápido y seguro.</li>
            <li className="footer-apple-item footer-apple-copyright">&copy; 2024 Kai Maki. Todos los derechos reservados.</li>
          </ul>
        </div>
        <div className="footer-apple-col">
          <h4 className="footer-apple-title" style={{color:'#222',transition:'color 0.2s'}} onMouseOver={e=>e.currentTarget.style.color='#f97316'} onMouseOut={e=>e.currentTarget.style.color='#222'}>Contáctanos</h4>
          <ul className="footer-apple-list">
            <li className="footer-apple-item">Santa Anita - Lima, Perú</li>
            <li className="footer-apple-item">
              <a href="mailto:hola.kaimaki@gmail.com" className="footer-link">
                hola.kaimaki@gmail.com
              </a>
            </li>
            <li className="footer-apple-item">
              <a href="tel:+51927106471" className="footer-link">
                +51 927 106 471
              </a>
            </li>
          </ul>
        </div>
        <div className="footer-apple-col">
          <h4 className="footer-apple-title" style={{color:'#222',transition:'color 0.2s'}} onMouseOver={e=>e.currentTarget.style.color='#f97316'} onMouseOut={e=>e.currentTarget.style.color='#222'}>Servicios</h4>
          <ul className="footer-apple-list">
            <li className="footer-apple-item">Plomería</li>
            <li className="footer-apple-item">Cerrajería</li>
            <li className="footer-apple-item">Electricista</li>
            <li className="footer-apple-item">Limpieza</li>
            <li className="footer-apple-item">Jardinería</li>
            <li className="footer-apple-item">Reparaciones Generales</li>
          </ul>
        </div>
        <div className="footer-apple-col">
          <h4 className="footer-apple-title" style={{color:'#222',transition:'color 0.2s'}} onMouseOver={e=>e.currentTarget.style.color='#f97316'} onMouseOut={e=>e.currentTarget.style.color='#222'}>Compañía</h4>
          <ul className="footer-apple-list">
            <li className="footer-apple-item"><a href="/about-us" className="footer-link">Sobre Nosotros</a></li>
            <li className="footer-apple-item">Síguenos:</li>
            <li className="footer-apple-social">
              <a href="https://www.facebook.com/profile.php?id=61568658249703" target="_blank" rel="noopener noreferrer">
                <img src={logoFacebook} alt="Facebook" />
              </a>
              <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">
                <img src={logoTwitter} alt="Twitter" />
              </a>
              <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
                <img src={logoInstagram} alt="Instagram" />
              </a>
            </li>
          </ul>
        </div>
      </div>
      <div className="footer-apple-bottom">
        <span>
          © 2024 Kai Maki. Todos los derechos reservados.
          <span className="footer-apple-sep">|</span>
          <a href="/" className="footer-link">Política de privacidad</a>
          <span className="footer-apple-sep">|</span>
          <a href="/" className="footer-link">Aviso legal</a>
          <span className="footer-apple-sep">|</span>
          <a href="/" className="footer-link">Mapa del sitio</a>
        </span>
      </div>
    </footer>
  );
};

export default Footer;
