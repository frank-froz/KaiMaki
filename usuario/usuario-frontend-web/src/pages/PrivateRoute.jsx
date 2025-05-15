// src/components/PrivateRoute.jsx
import { useContext } from 'react'
import { Navigate } from 'react-router-dom'
import { AuthContext } from '../context/AuthContext'

export function PrivateRoute({ children }) {
  const { user } = useContext(AuthContext)

  // Si no hay usuario autenticado, redirige al login
  return user ? children : <Navigate to="/login" replace />
}
