import React, { createContext, useState, useEffect } from 'react';
import { jwtDecode } from 'jwt-decode';
import { perfil } from '../services/authService';

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('token');
    console.log('[AuthContext] Token encontrado en localStorage:', token);

    if (!token) {
      setLoading(false);
      return;
    }

    try {
      const decoded = jwtDecode(token);

      if (decoded.exp * 1000 < Date.now()) {
        console.warn('Token expirado');
        logout();
        setLoading(false);
        return;
      }      // Usar el servicio que ya tiene el interceptor configurado
      perfil()
          .then(res => {
            console.log('[AuthContext] Perfil cargado:', res.data);
            setUser(res.data);
          })
          .catch(err => {
            console.error('[AuthContext] Error al obtener perfil:', err);
            logout();
          })
          .finally(() => {
            setLoading(false);
          });

    } catch (error) {
      console.error('[AuthContext] Token inválido:', error);
      logout();
      setLoading(false);
    }
  }, []);
  function login(token, callback) {
    localStorage.setItem('token', token);
    console.log('[AuthContext] Token guardado, el interceptor se encargará de agregarlo');

    perfil()
        .then(res => {
          console.log('[AuthContext] Perfil cargado después del login:', res.data);
          setUser(res.data);
          if (callback) callback();
        })
        .catch(err => {
          console.error('[AuthContext] Error al obtener perfil después del login:', err);
          logout();
        });
  }


  function logout() {
    localStorage.removeItem('token');
    setUser(null);
  }

  return (
      <AuthContext.Provider value={{ user, loading, login, logout }}>
        {loading ? (
            <p className="text-center mt-20">Cargando sesión...</p>
        ) : (
            children
        )}
      </AuthContext.Provider>
  );
}
export default AuthProvider;