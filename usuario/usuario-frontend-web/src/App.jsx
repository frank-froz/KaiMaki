import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { PrivateRoute } from './components/PrivateRoute';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import Dashboard from './pages/Dashboard';
import LandingPage from './pages/LandingPage';
import TrabajadoresDisponibles from './pages/TrabajadoresPage';
import PerfilPage from './pages/PerfilPage';
import AboutPage from './pages/AboutPage';
import Footer from './components/Footer';
import './css/Global.css';

export default function App() {
    return (
        <BrowserRouter>
            <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
                <main style={{ flex: 1 }}>
                    <Routes>
                        {/* Rutas públicas */}
                        <Route path="/" element={<LandingPage />} />
                        <Route path="/login" element={<LoginPage />} />
                        <Route path="/register" element={<RegisterPage />} />
                        <Route path="/about" element={<AboutPage />} />

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
                            path="/trabajadores"
                            element={
                                <PrivateRoute>
                                    <TrabajadoresDisponibles />
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
                        />

                        <Route
                            path="/perfil/:id"
                            element={
                                <PrivateRoute>
                                    <PerfilPage />
                                </PrivateRoute>
                            }
                        />

                        {/* Ruta por defecto si no se encuentra ninguna */}
                        <Route path="*" element={<LandingPage />} />
                    </Routes>
                    </main>
                <Footer />
            </div>
        </BrowserRouter>
        
    );
}
