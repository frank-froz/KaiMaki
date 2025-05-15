// src/context/AuthContext.jsx
import React, { createContext, useState } from 'react'

export const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)

  function login(token) {
    localStorage.setItem('token', token)
    // podrías decodificar aquí el payload del JWT si quieres
    // setUser(decodedUser)
  }

  function logout() {
    localStorage.removeItem('token')
    setUser(null)
  }

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}
