// src/pages/Dashboard.jsx
import { useContext, useEffect } from 'react'
import { AuthContext } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'

export default function Dashboard() {
  const { user, logout } = useContext(AuthContext)
  const navigate = useNavigate()

  useEffect(() => {
    if (!user) {
      navigate('/login')
    }
  }, [user, navigate])

  const handleLogout = () => {
    logout()
    navigate('/login')
  }

  return (
      <div>
        <h1>Dashboard</h1>

        {user ? (
            <div>
              <p>Bienvenido, {user.email || user.sub}!</p>
              <button onClick={handleLogout}>Cerrar sesión</button>
            </div>
        ) : (
            <p>Cargando...</p>
        )}
      </div>
  )
}
