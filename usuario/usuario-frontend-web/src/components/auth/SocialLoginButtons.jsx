// src/components/SocialLoginButtons.jsx
import { useContext } from 'react'
import { GoogleLogin } from '@react-oauth/google'

import { AuthContext } from '../../context/AuthContext'
import { oauthGoogle } from '../../services/authService'
import { useNavigate } from 'react-router-dom'

export function SocialLoginButtons() {
  const { login } = useContext(AuthContext)
  const navigate = useNavigate()

  const handleGoogle = async (res) => {
    try {
      if (!res.credential) throw new Error('No se recibió token de Google')

      const { data } = await oauthGoogle(res.credential)

      if (data.token) {
        login(data.token)
        navigate('/dashboard')
      } else {
        console.error('Token JWT no recibido desde el backend')
      }
    } catch (err) {
      console.error('Error al autenticar con Google:', err)
    }
  }

  return (
      <GoogleLogin
          onSuccess={handleGoogle}
          onError={() => console.error('Error con el inicio de sesión de Google')}
      />
  )
}
