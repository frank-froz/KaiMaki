// src/components/Header.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import '../css/Header.css'; // Asegúrate de tener este archivo CSS

const Header = () => {
  return (
    <header className="app-header">
      <div className="logo">
        <Link to="/">Kaimaki</Link> {/* Logo que redirige a la página principal */}
      </div>
      <nav>
        <ul>
          <li><Link to="/home">Home</Link></li>
            <li><Link to="/services">Servicios</Link></li>
            <li><Link to="/help">Ayuda</Link></li>
          <li><Link to="/about">Nosotros</Link></li>
          <li><Link to="/login">Iniciar sesión</Link></li>
          <li><Link to="/register" className="register-button">Registrarse</Link></li>
        </ul>
      </nav>
    </header>
  );
}

export default Header;
