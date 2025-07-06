import React, { useContext, useState, useEffect, useRef } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { HashLink } from 'react-router-hash-link';
import { AuthContext } from '../../context/AuthContext';
import '../../styles/layout/Header.css';

const Header = () => {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();
  const location = useLocation();
  const [menuOpen, setMenuOpen] = useState(false);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [hideHeader, setHideHeader] = useState(false);
  const dropdownRef = useRef(null);
  const lastScrollY = useRef(window.scrollY);

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

  useEffect(() => {
    let ticking = false;
    const handleHideOnScroll = () => {
      const currentY = window.scrollY;
      if (!ticking) {
        window.requestAnimationFrame(() => {
          if (currentY > lastScrollY.current && currentY > 80) {
            setHideHeader(true); // Baja: ocultar
          } else {
            setHideHeader(false); // Sube: mostrar
          }
          lastScrollY.current = currentY;
          ticking = false;
        });
        ticking = true;
      }
    };
    window.addEventListener('scroll', handleHideOnScroll);
    return () => window.removeEventListener('scroll', handleHideOnScroll);
  }, []);

  // Helper para saber si la ruta está activa
  const isActive = (path) => {
    if (path === '/home') return location.pathname === '/' || location.pathname === '/home';
    if (path === '/trabajadores') return location.pathname.startsWith('/trabajadores');
    if (path === '/about') return location.pathname.startsWith('/about');
    if (path === '/#todo-en-3-pasos' || path === '/ayuda') return location.hash === '#todo-en-3-pasos' || location.pathname === '/ayuda';
    return location.pathname === path;
  };

  return (
    <header className={`app-header${hideHeader ? ' hide-header' : ''}`}>
      <div className="header-left">
        <div className="logo">
          <Link to="/">
            <img src="/kaimaki_imagotipo.png" alt="Kaimaki logo" style={{height:'80px', width:'auto', maxWidth:'240px', verticalAlign:'middle', display:'block'}} />
          </Link>
        </div>
        <div className="menu-toggle" onClick={() => setMenuOpen(!menuOpen)}>
          <span></span>
          <span></span>
          <span></span>
        </div>
      </div>

      <nav className={`nav ${menuOpen ? 'open' : ''}`}>
        <ul className={`nav-links ${menuOpen ? 'open' : ''}`}>
          <li>
            <Link to="/home" className={isActive('/home') ? 'nav-link-active' : ''}>Home</Link>
          </li>
          <li>
            <Link to="/trabajadores" className={isActive('/trabajadores') ? 'nav-link-active' : ''}>Trabajadores</Link>
          </li>
          <li>
            <HashLink smooth to="/#todo-en-3-pasos" className={isActive('/#todo-en-3-pasos') ? 'nav-link-active' : ''}>Ayuda</HashLink>
          </li>
          <li>
            <Link to="/about" className={isActive('/about') ? 'nav-link-active' : ''}>Nosotros</Link>
          </li>

          {user ? (
            <li
              className={`user-menu ${dropdownOpen ? 'open' : ''}`}
              ref={dropdownRef}
            >
              <button className="user-name user-avatar-btn" onClick={toggleDropdown} style={{display:'flex',alignItems:'center',gap:'0.7rem',background:'none',border:'none',padding:0,cursor:'pointer'}}>
                <img
                  src={user.fotoPerfil || '/avatar.png'}
                  alt="Foto de perfil"
                  className="header-user-avatar"
                  style={{width:32,height:32,borderRadius:'50%',objectFit:'cover',border:'2px solid #ff9800',background:'#fff'}}
                />
              </button>
              {dropdownOpen && (
                <div className="dropdown-menu user-dropdown-bubble">
                  <div className="dropdown-user-greeting">
                    ¡Hola,<br /><span style={{fontWeight:'bold',color:'#ff9800'}}>{user.nombre || user.email || 'Usuario'}</span>!
                  </div>
                  <hr className="dropdown-sep" />
                  <ul className="dropdown-list" role="menu">
                    <li role="menuitem"><Link to="/perfil">Visitar mi perfil</Link></li>
                    <li role="menuitem"><Link to="/dashboard">Ir al menú</Link></li>
                    <hr className="dropdown-sep" />
                    <li role="menuitem" className="logout" onClick={handleLogout}>
                      Cerrar sesión
                    </li>
                  </ul>
                </div>
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
