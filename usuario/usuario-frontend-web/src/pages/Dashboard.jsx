// src/pages/Dashboard.jsx
import { useContext } from 'react'
import { AuthContext } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'

export default function Dashboard() {
  const { user, logout } = useContext(AuthContext)
  const navigate = useNavigate()

  // Manejo de logout
  const handleLogout = () => {
    logout()
    navigate('/login') // Redirige al login después de hacer logout
  }

  return (
    <div>
      <h1>Dashboard</h1>

      {/* Si hay un usuario autenticado, muestra su información */}
      {user ? (
        <div>
          <p>Bienvenido, {user.email}!</p> {/* Muestra la información del usuario */}
          <button onClick={handleLogout}>Cerrar sesión</button>
        </div>
      ) : (
        <p>No estás autenticado.</p>
      )}
    </div>
  )
}
