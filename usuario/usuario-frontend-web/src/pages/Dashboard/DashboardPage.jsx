import React, { useContext, useEffect } from 'react';
import '../../styles/pages/DashboardPage.css';
import { AuthContext } from '../../context/AuthContext';
import { useNavigate, Link } from 'react-router-dom';
import { FaComments } from 'react-icons/fa';
import Header from '../../components/layout/Header';

export default function DashboardPage() {
  const { user, logout } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) navigate('/login');
  }, [user, navigate]);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const cards = [
    { title: 'Perfil', desc: 'Gestiona tus datos', to: '/perfil', icon: '👤' },
    { title: 'Trabajadores', desc: 'Busca trabajadores fácilmente', to: '/trabajadores', icon: '🛠️' },
    { title: 'Reportes', desc: 'Genera tus reportes', to: '/reportes', icon: '📊' },
    { title: 'Solicitudes', desc: 'Consulta tus solicitudes', to: '/solicitudes', icon: '📋' },
    { title: 'Reclamos', desc: 'Haz un reclamo', to: '/reclamos/nuevo', icon: '⚠️' },
    { title: 'Configuración', desc: 'Ajustes de perfil', to: '/perfil/configuracion', icon: '⚙️' },
  ];

  return (
    <>
      <Header />
      <div className="dashboard-container">
        <h1 className="dashboard-title">
          Bienvenido, {user?.nombre || user?.sub || 'Usuario'} 👋
        </h1>
        <div className="dashboard-content">
          <button
            className="floating-chat-button"
            title="Ir al chat"
            onClick={() => navigate('/chat')}
            onMouseOver={e => e.currentTarget.classList.add('hover')}
            onMouseOut={e => e.currentTarget.classList.remove('hover')}
          >
            <FaComments />
          </button>

          <div className="dashboard-grid">
            {cards.map(({ title, desc, to, icon }, i) => (
              <div className="dashboard-card" key={i}>
                <div className="dashboard-card-header">
                  <span className="dashboard-icon">{icon}</span>
                  <h2>{title}</h2>
                </div>
                <p>{desc}</p>
                <Link to={to} className="dashboard-button">
                  Ingresar
                </Link>
              </div>
            ))}
          </div>

          <div className="dashboard-footer">
            <button onClick={handleLogout} className="logout-button">
              Cerrar sesión
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
