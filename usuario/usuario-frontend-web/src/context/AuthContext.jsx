import React, { createContext, useState, useEffect } from 'react';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

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
      }

      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

      axios.get(`${import.meta.env.VITE_API_URL}/perfil`)
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
    axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

    //const decoded = jwtDecode(token);

    axios.get(`${import.meta.env.VITE_API_URL}/perfil`)
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
    delete axios.defaults.headers.common['Authorization'];
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