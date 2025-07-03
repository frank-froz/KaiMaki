import React, { useContext, useState, useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { HashLink } from 'react-router-hash-link';
import { AuthContext } from '../../context/AuthContext';
import '../../styles/layout/Header.css';

const Header = () => {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const [menuOpen, setMenuOpen] = useState(false);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const dropdownRef = useRef(null);

  const handleLogout = () => {
    setDropdownOpen(false);
    logout();
    navigate('/');
  };

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const handleClickOutside = (event) => {
    if (
      dropdownRef.current &&
      !dropdownRef.current.contains(event.target)
    ) {
      setDropdownOpen(false);
    }
  };

  const handleScroll = () => {
    const header = document.querySelector('.app-header');
    if (window.scrollY > 10) {
      header.classList.add('scrolled');
    } else {
      header.classList.remove('scrolled');
    }
  };

  useEffect(() => {
    document.addEventListener('click', handleClickOutside);
    window.addEventListener('scroll', handleScroll);
    return () => {
      document.removeEventListener('click', handleClickOutside);
      window.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <header className="app-header">
      <div className="header-left">
        <div className="logo">
          <Link to="/">Kaimaki</Link>
        </div>
        <div className="menu-toggle" onClick={() => setMenuOpen(!menuOpen)}>
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>

      <nav className={`nav ${menuOpen ? 'open' : ''}`}>
        <ul className={`nav-links ${menuOpen ? 'open' : ''}`}>
          <li><Link to="/home">Home</Link></li>
          <li><Link to="/trabajadores">Trabajadores</Link></li>
          <li><HashLink smooth to="/#todo-en-3-pasos">Ayuda</HashLink></li>
          <li><Link to="/about">Nosotros</Link></li>

          {user ? (
            <li
              className={`user-menu ${dropdownOpen ? 'open' : ''}`}
              ref={dropdownRef}
            >
              <button className="user-name" onClick={toggleDropdown}>
                {user.nombre || user.email || 'Usuario'}
              </button>
              {dropdownOpen && (
                <ul className="dropdown-menu" role="menu">
                  <li role="menuitem"><Link to="/perfil">Mi perfil</Link></li>
                  <li role="menuitem"><Link to="/dashboard">Dashboard</Link></li>
                  <li role="menuitem" onClick={handleLogout}>Cerrar sesión</li>
                </ul>
              )}
            </li>
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
};

export default Header;
