// src/components/SocialLoginButtons.jsx
import { useContext } from 'react'
import { GoogleLogin } from '@react-oauth/google'

import { AuthContext } from '../context/AuthContext'
import { oauthGoogle, oauthFacebook } from '../services/authService'

export function SocialLoginButtons() {
  const { login } = useContext(AuthContext)

  const handleGoogle = async res => {
    if (res.credential) {
      const { data } = await oauthGoogle(res.credential)
      login(data.token)
    }
  }


  return (
    <>
      <GoogleLogin
        onSuccess={handleGoogle}
        onError={() => console.error('Error con Google')}
      />

    </>
  )
}
