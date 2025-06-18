// src/components/PrivateRoute.jsx
import { useContext } from 'react'
import { Navigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContext'

export function PrivateRoute({ children }) {
  const { user, loading } = useContext(AuthContext)

  if (loading) {
    return <p>Cargando sesión...</p> // o un spinner si prefieres
  }

  return user ? children : <Navigate to="/login" />
}