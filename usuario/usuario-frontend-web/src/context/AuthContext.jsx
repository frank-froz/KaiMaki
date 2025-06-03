import React, { createContext, useState, useEffect } from 'react'
import { jwtDecode } from 'jwt-decode';



export const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)

  useEffect(() => {
    const token = localStorage.getItem('token')
    if (token) {
      try {
        const decoded = jwtDecode(token)
        setUser(decoded)
      } catch (error) {
        console.error('Token inválido', error)
        logout()
      }
    }
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
      <AuthContext.Provider value={{ user, login, logout }}>
        {children}
      </AuthContext.Provider>
  )
}
