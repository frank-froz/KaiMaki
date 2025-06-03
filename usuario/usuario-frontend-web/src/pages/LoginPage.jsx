// src/pages/LoginPage.jsx
import { useState, useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { login as apiLogin, oauthGoogle } from '../services/authService'
import { AuthContext } from '../context/AuthContext'
import { GoogleLoginButton } from '../components/GoogleLoginButton'
import Header from '../components/Header'
import '../css/LoginPage.css'

export default function LoginPage() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const { login } = useContext(AuthContext)
  const navigate = useNavigate()

  const handleEmailLogin = async (e) => {
    e.preventDefault()
    setError('')

    if (!email || !password) {
      setError('Por favor ingresa email y contraseña')
      return
    }

    try {
      const res = await apiLogin({ correo: email, contrasena: password })
      const token = res.data.token
      if (token) {
        login(token)
        navigate('/dashboard')
      } else {
        setError('Credenciales inválidas')
      }
    } catch (err) {
      setError(err.response?.data || 'Error al iniciar sesión')
    }
  }

  const handleGoogleLogin = async (response) => {
    const idToken = response.credential;
    console.log("🔐 Google ID Token recibido:", idToken); // 👈 ESTE LOG

    try {
      const res = await oauthGoogle(idToken);
      const token = res.data.token || res.data.jwt;
      if (token) {
        login(token);
        navigate('/dashboard');
      } else {
        setError('Error al iniciar sesión con Google');
      }
    } catch (err) {
      setError('Error al iniciar sesión con Google');
      console.error(err);
    }
  };


  const handleGoogleError = (error) => {
    console.error('Google login error:', error)
    setError('Error al iniciar sesión con Google')
  }

  return (
      <div>
        <Header />
        <div className="login-container">
          <h1>Inicia sesión</h1>

          {/* Formulario login con email y password */}
          <form onSubmit={handleEmailLogin} className="login-form">
            <input
                type="email"
                placeholder="Correo electrónico"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
            />
            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
            {error && <p className="error">{error}</p>}
            <button type="submit" className="email-login-button">Iniciar sesión</button>
          </form>

          <div className="divider">O inicia sesión con</div>

          {/* Botón login con Google */}
          <div className="google-login-button-wrapper">
            <GoogleLoginButton onSuccess={handleGoogleLogin} onError={handleGoogleError} />
          </div>
        </div>
      </div>
  )

}
