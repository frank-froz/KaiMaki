// src/pages/Dashboard.jsx
import { useContext, useEffect } from 'react'
import { AuthContext } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import { FaComments } from 'react-icons/fa'
import Header from '../components/Header'

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
        <>
            <Header /> {/* Aquí ya se verá el nombre si está logueado */}

            <div style={{ padding: '2rem' }}>
                <h1>Bienvenido al Panel de Usuario</h1>

                <button
                    style={{
                        position: 'absolute', right: 24, top: 100, background: 'none', border: 'none', cursor: 'pointer', fontSize: 28
                    }}
                    title="Ir al chat"
                    onClick={() => navigate('/chat')}
                >
                    <FaComments />
                </button>

                {user ? (
                    <div>
                        <p>Hola, {user.nombre || user.sub}</p>
                        <p>Este es tu panel de control. Aquí puedes ver tus datos, acceder al chat, y más.</p>
                        <button onClick={handleLogout}>Cerrar sesión</button>
                    </div>
                ) : (
                    <p>Cargando...</p>
                )}
            </div>
        </>
    )
}
