// src/components/GoogleLoginButton.jsx
import { GoogleLogin } from '@react-oauth/google'

export function GoogleLoginButton({ onSuccess, onError }) {
  return (
    <GoogleLogin
      onSuccess={onSuccess}   // Callback cuando el login sea exitoso
      onError={onError}       // Callback cuando el login falle
      useOneTap                // Activa la opción de "One Tap" (aparece como un pop-up en algunos casos)
    />
  )
}
