import React, { createContext, useState, useEffect } from 'react'
import { jwtDecode } from 'jwt-decode';


export const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    const token = localStorage.getItem('token')
    console.log('[AuthContext] Token encontrado en localStorage:', token)

    if (token) {
      try {
        const decoded = jwtDecode(token)
        console.log('[AuthContext] Usuario decodificado:', decoded)

        if (decoded.exp * 1000 < Date.now()) {
          console.warn('⚠ Token expirado')
          throw new Error('Token expirado')
        }

        setUser(decoded)
        console.log('[AuthContext] Usuario seteado en contexto')
      } catch (error) {
        console.error('[AuthContext] Token inválido al cargar:', error)
        logout()
      }
    }

    setLoading(false)
  }, [])

  function login(token) {
    localStorage.setItem('token', token)
    const decodedUser = jwtDecode(token)
    setUser(decodedUser)
  }

  function logout() {
    localStorage.removeItem('token')
    setUser(null)
  }

  return (
      <AuthContext.Provider value={{ user, loading, login, logout }}>
        {children}
      </AuthContext.Provider>
      )
}