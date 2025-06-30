import React, { useContext, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { HashLink } from 'react-router-hash-link';
import { AuthContext } from '../context/AuthContext';
import '../css/Header.css';

const Header = () => {
    const { user, logout } = useContext(AuthContext);
    const navigate = useNavigate();
    const [menuOpen, setMenuOpen] = useState(false);

    const handleLogout = () => {
        logout();
        navigate('/');
    };

    const toggleMenu = () => {
        setMenuOpen(!menuOpen);
    };

    return (
        <header className="app-header">
            <div className="logo">
                <Link to="/">Kaimaki</Link>
            </div>

            <nav>
                <ul className="nav-links">
                    <li><Link to="/home">Home</Link></li>
                    <li><Link to="/trabajadores">Trabajadores</Link></li>
                    <li>
                    <HashLink smooth to="/#todo-en-3-pasos">Ayuda</HashLink>
                    </li>
                    <li><Link to="/about">Nosotros</Link></li>

                    {user ? (
                        <li className="user-menu">
              <span className="user-name" onClick={toggleMenu}>
                {user.nombre || user.email || 'Usuario'}
              </span>
                            {menuOpen && (
                                <ul className="dropdown-menu">
                                    <li><Link to="/perfil">Mi perfil</Link></li>
                                    <li><Link to="/dashboard">Dashboard</Link></li>
                                    <li onClick={handleLogout}>Cerrar sesión</li>
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
