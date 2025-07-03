// Footer.jsx
import React from 'react';
import '../../styles/layout/Footer.css';
import logoFacebook from '../../assets/logoFacebook.png';
import logoTwitter from '../../assets/logoTwitter.png';
import logoInstagram from '../../assets/logoInstagram.png';

const Footer = () => {
  return (
    <footer>
      <div className="footer-container">
        {/* Sección KaiMaki */}
        <div>
          <h3 className="footer-title">Kai Maki</h3>
          <p>Conectamos clientes con profesionales para servicios de calidad en el hogar.</p>
          <p>Fácil, rápido y seguro.</p>
          <p style={{ marginTop: '1rem' }}>&copy; 2024 Kai Maki. Todos los derechos reservados.</p>
        </div>

        {/* Contacto */}
        <div>
          <h3 className="footer-title">Contáctanos</h3>
          <p>Santa Anita - Lima, Perú</p>
          <p>
            <a href="mailto:hola.kaimaki@gmail.com" className="footer-link" target="_blank" rel="noopener noreferrer">
              hola.kaimaki@gmail.com
            </a>
          </p>
          <p>
            <a href="tel:+51927106471" className="footer-link">+51 927 106 471</a>
          </p>
        </div>

        {/* Servicios */}
        <div>
          <h3 className="footer-title">Servicios</h3>
          <ul className="footer-list">
            <li>Plomería</li>
            <li>Cerrajería</li>
            <li>Electricista</li>
            <li>Limpieza</li>
            <li>Jardinería</li>
            <li>Reparaciones Generales</li>
          </ul>
        </div>

        {/* Compañía */}
        <div>
          <h3 className="footer-title">Compañía</h3>
          <ul className="footer-list">
            <li><a href="/about-us" className="footer-link">Sobre Nosotros</a></li>
            <li>Síguenos:</li>
          </ul>
          <div className="footer-social">
            <a href="https://www.facebook.com/profile.php?id=61568658249703" target="_blank" rel="noopener noreferrer">
              <img src={logoFacebook} alt="Facebook" />
            </a>
            <a href="https://twitter.com" target="_blank" rel="noopener noreferrer">
              <img src={logoTwitter} alt="Twitter" />
            </a>
            <a href="https://instagram.com" target="_blank" rel="noopener noreferrer">
              <img src={logoInstagram} alt="Instagram" />
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
