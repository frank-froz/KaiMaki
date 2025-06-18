// src/pages/Dashboard.jsx
import { useContext, useEffect } from 'react'
import { AuthContext } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import { FaComments } from 'react-icons/fa'

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

        <button
          style={{
            position: 'absolute', right: 24, top: 24, background: 'none', border: 'none', cursor: 'pointer', fontSize: 28
          }}
          title="Ir al chat"
          onClick={() => navigate('/chat')}
        >
          <FaComments />
        </button>

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
