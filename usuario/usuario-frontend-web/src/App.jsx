import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { PrivateRoute } from './components/PrivateRoute';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Dashboard from './pages/Dashboard';
import LandingPage from './pages/LandingPage';
import TrabajadoresDisponibles from './pages/TrabajadoresPage';
import PerfilPage from './pages/PerfilPage';
import ChatPage from './pages/ChatPage';

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Rutas públicas */}
                <Route path="/" element={<LandingPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/trabajadores" element={<TrabajadoresDisponibles />} />

                {/* Rutas protegidas */}
                <Route
                    path="/dashboard"
                    element={
                        <PrivateRoute>
                            <Dashboard />
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/perfil"
                    element={
                        <PrivateRoute>
                            <PerfilPage />
                        </PrivateRoute>
                    }
                />                <Route
                    path="/perfil/:id"
                    element={
                        <PrivateRoute>
                            <PerfilPage />
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/mi-perfil"
                    element={
                        <PrivateRoute>
                            <PerfilPage />
                        </PrivateRoute>
                    }
                />

                {/* Rutas para el Chat */}
                <Route
                    path="/chat"
                    element={
                        <PrivateRoute>
                            <ChatPage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/chat/:roomId"
                    element={
                        <PrivateRoute>
                            <ChatPage />
                        </PrivateRoute>
                    }
                />

                {/* Ruta por defecto si no se encuentra ninguna */}
                <Route path="*" element={<LandingPage />} />
            </Routes>
        </BrowserRouter>
    );
}
