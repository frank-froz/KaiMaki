// src/components/Header.jsx
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../css/Header.css'; 

const Header = ({ isLoggedIn = false, userName = "Usuario" }) => {
  const navigate = useNavigate();
  const handleLogout = () => {
    //  limpiar el estado de autenticación si lo tienes
    navigate('/');
  };

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
          {isLoggedIn ? (
            <>
              <li><Link to="/profile">Perfil</Link></li>
              <li><Link to="/settings">Configuración</Link></li>
              <li><span onClick={handleLogout}>Cerrar sesión</span></li>
            </>
          ) : (
            <>
              <li><Link to="/login">Iniciar sesión</Link></li>
              <li><Link to="/register" className="register-button">Registrarse</Link></li>
            </>
          )}
        </ul>
      </nav>
    </header>
  );
}

export default Header;
