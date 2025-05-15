// src/main.jsx
import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import { AuthProvider } from './context/AuthContext' // Importa tu contexto de autenticación
import { GoogleOAuthProvider } from '@react-oauth/google' // Proveedor de Google OAuth

// Proveedor de Google OAuth - Necesario para el login de Google
const googleClientId = import.meta.env.VITE_GOOGLE_CLIENT_ID;

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    {/* Proveedor para la autenticación con Google */}
    <GoogleOAuthProvider clientId={googleClientId}>
      {/* Proveedor de contexto de autenticación */}
      <AuthProvider>
        <App />
      </AuthProvider>
    </GoogleOAuthProvider>
  </React.StrictMode>
)
