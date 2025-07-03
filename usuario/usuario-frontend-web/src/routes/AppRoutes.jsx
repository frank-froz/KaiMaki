import { Routes, Route } from 'react-router-dom';
import { PublicRoutes } from './PublicRoutes';
import { AuthLayout } from './AuthRoutes';
import { DashboardLayout } from './DashboardRoutes';
import { ChatLayout } from './ChatRoutes';
import { TrabajadoresLayout } from './TrabajadoresRoutes';

import LandingPage from '../pages/Home/LandingPage';
import AboutPage from '../pages/Home/AboutPage';
import LoginPage from '../pages/Auth/LoginPage';
import RegisterPage from '../pages/Auth/RegisterPage';
import Dashboard from '../pages/Dashboard/DashboardPage';
import PerfilPage from '../pages/Dashboard/PerfilPage';
import ChatPage from '../pages/Chat/ChatPage';
import TrabajadoresDisponibles from '../pages/Trabajadores/TrabajadoresPage';

import { PrivateRoute } from '../components/layout/PrivateRoute';

export default function AppRoutes() {
  return (
    <Routes>
      {/* Rutas públicas */}
      <Route element={<PublicRoutes />}>
        <Route path="/" element={<LandingPage />} />
        <Route path="/about" element={<AboutPage />} />
      </Route>

      {/* Rutas auth */}
      <Route element={<AuthLayout />}>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
      </Route>

      {/* Dashboard */}
      <Route element={<PrivateRoute><DashboardLayout /></PrivateRoute>}>
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/perfil" element={<PerfilPage />} />
        <Route path="/perfil/:id" element={<PerfilPage />} />
        <Route path="/mi-perfil" element={<PerfilPage />} />
      </Route>

      {/* Trabajadores */}
      <Route element={<PrivateRoute><TrabajadoresLayout /></PrivateRoute>}>
        <Route path="/trabajadores" element={<TrabajadoresDisponibles />} />
      </Route>

      {/* Chat */}
      <Route element={<PrivateRoute><ChatLayout /></PrivateRoute>}>
        <Route path="/chat" element={<ChatPage />} />
        <Route path="/chat/:roomId" element={<ChatPage />} />
      </Route>

      {/* Fallback */}
      <Route path="*" element={<LandingPage />} />
    </Routes>
  );
}
