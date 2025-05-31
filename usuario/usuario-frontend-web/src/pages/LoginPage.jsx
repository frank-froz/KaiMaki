import { useState } from 'react'
import { useNavigate } from 'react-router-dom'              // ← IMPORTACIÓN NUEVA para redirección
import { GoogleLoginButton } from '../components/GoogleLoginButton'
import EmailIcon from '../assets/email-icon.svg'
import '../css/LoginPage.css'
import Header from '../components/Header'
import { oauthGoogle } from '../services/authService'        // ← IMPORTACIÓN NUEVA para llamar backend

export default function LoginPage() {
  const [step, setStep] = useState(0)
  const [email, setEmail] = useState('')
  const [name, setName] = useState('')
  const [verificationCode, setVerificationCode] = useState('')
  const [error, setError] = useState('')

  const navigate = useNavigate()                             // ← HOOK NUEVO para redirigir rutas

  // Validación del correo
  const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/
    return emailRegex.test(email)
  }

  // Validación del nombre
  const validateName = (name) => {
    return name.length > 0
  }

  // Validación del código
  const validateCode = (code) => {
    return code.length === 6
  }

  const handleEmailButtonClick = () => {
    setStep(1)
  }

  const handleEmailChange = (e) => {
    setEmail(e.target.value)
  }

  const handleContinueEmail = () => {
    if (!email || !validateEmail(email)) {
      setError('Por favor, ingresa un correo válido.')
      return
    }
    setError('')
    setStep(2)
  }

  const handleNameChange = (e) => {
    setName(e.target.value)
  }

  const handleContinueName = () => {
    if (!name || !validateName(name)) {
      setError('Por favor, ingresa tu nombre.')
      return
    }
    setError('')
    setStep(3)
  }

  const handleCodeChange = (e) => {
    setVerificationCode(e.target.value)
  }

  const handleContinueCode = () => {
    if (!verificationCode || !validateCode(verificationCode)) {
      setError('Por favor, ingresa un código válido.')
      return
    }
    setError('')
    setStep(4)
  }

  const handleBack = () => {
    setStep(step - 1)
  }

  // ---------------------------------------------
  // MODIFICACIÓN - FUNCIONES PARA LOGIN GOOGLE CON REDIRECCIÓN
  // ---------------------------------------------

  const handleGoogleLogin = async (response) => {
    console.log('Google login response', response)
    const idToken = response.credential
    try {
      const res = await oauthGoogle(idToken)  // Llamada backend para validar token y obtener JWT
      const jwt = res.data.jwt
      localStorage.setItem('token', jwt)
      navigate('/dashboard')                   // ← REDIRECCIÓN tras login exitoso
      console.log('Login exitoso, JWT:', jwt)
    } catch (error) {
      console.error('Error en el login con Google:', error)
    }
  }

  const handleGoogleError = (error) => {
    console.error('Error en el login con Google:', error)
  }

  // ---------------------------------------------

  return (
    <div>
      <Header />
      <div className="login-container">
        <div className="login-card">
          {/* Paso 0: título y botones de login */}
          {step === 0 && (
            <>
              <h1>Inicia sesión o regístrate en un momento</h1>
              <p>Usa tu correo electrónico o otro servicio para acceder a Kaimaki.</p>
              <div className="login-buttons">
                {/* --------- USO DEL BOTÓN GOOGLE --------- */}
                <GoogleLoginButton
                  onSuccess={handleGoogleLogin}   // ← PROPS AGREGADAS para manejar login Google
                  onError={handleGoogleError}     // ← PROPS AGREGADAS para manejar errores
                />
                <button
                  className="email-login-button"
                  onClick={handleEmailButtonClick}
                >
                  <img src={EmailIcon} alt="Email icon" width="20" height="20" />
                  Usar un correo electrónico
                </button>
              </div>
            </>
          )}

          {/* Paso 1: Formulario correo */}
          {step === 1 && (
            <div>
              <div className="step-header">
                <button onClick={handleBack} className="back-button">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                    <path fill="currentColor" d="m14.8 16.382-4.205-4.205a.25.25 0 0 1 0-.354L14.8 7.618a.75.75 0 0 0-1.06-1.06l-4.206 4.204a1.75 1.75 0 0 0 0 2.475l4.206 4.205a.75.75 0 0 0 1.06-1.06"></path>
                  </svg>
                </button>
                <h2>Ingresa tu correo</h2>
              </div>
              <p>Comprobamos si tienes una cuenta y si no, te ayudaremos a crear una.</p>
              <input
                type="email"
                value={email}
                onChange={handleEmailChange}
                placeholder="Correo electrónico"
              />
              {error && <p className="error">{error}</p>}
              <button onClick={handleContinueEmail}>Continuar</button>
            </div>
          )}

          {/* Paso 2: Formulario nombre */}
          {step === 2 && (
            <div>
              <div className="step-header">
                <button onClick={handleBack} className="back-button">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                    <path fill="currentColor" d="m14.8 16.382-4.205-4.205a.25.25 0 0 1 0-.354L14.8 7.618a.75.75 0 0 0-1.06-1.06l-4.206 4.204a1.75 1.75 0 0 0 0 2.475l4.206 4.205a.75.75 0 0 0 1.06-1.06"></path>
                  </svg>
                </button>
                <h2>Ingresa tu nombre</h2>
              </div>
              <input
                type="text"
                value={name}
                onChange={handleNameChange}
                placeholder="Nombre completo"
              />
              {error && <p className="error">{error}</p>}
              <button onClick={handleContinueName}>Continuar</button>
            </div>
          )}

          {/* Paso 3: Formulario código */}
          {step === 3 && (
            <div>
              <div className="step-header">
                <button onClick={handleBack} className="back-button">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24">
                    <path fill="currentColor" d="m14.8 16.382-4.205-4.205a.25.25 0 0 1 0-.354L14.8 7.618a.75.75 0 0 0-1.06-1.06l-4.206 4.204a1.75 1.75 0 0 0 0 2.475l4.206 4.205a.75.75 0 0 0 1.06-1.06"></path>
                  </svg>
                </button>
                <h2>Introduce el código de verificación</h2>
              </div>
              <p>Te hemos enviado un código a {email} para terminar el registro.</p>
              <input
                type="text"
                value={verificationCode}
                onChange={handleCodeChange}
                placeholder="Código de verificación"
              />
              {error && <p className="error">{error}</p>}
              <button onClick={handleContinueCode}>Finalizar</button>
            </div>
          )}

          {/* Paso 4: Bienvenida */}
          {step === 4 && (
            <div>
              <h2>¡Bienvenido!</h2>
              <p>Tu cuenta ha sido creada exitosamente.</p>
              <button onClick={() => alert('Redirigiendo a perfil...')}>Ir a mi perfil</button>
            </div>
          )}

          <p className="terms">
            Al continuar, aceptas las <a href="/terms">Términos y condiciones de uso</a> de Kaimaki. Consulta nuestra <a href="/privacy-policy">Política de privacidad</a>.
          </p>
        </div>
      </div>
    </div>
  )
}
