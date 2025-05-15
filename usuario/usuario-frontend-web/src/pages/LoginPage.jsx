import { useState } from 'react';
import { GoogleLoginButton } from '../components/GoogleLoginButton';
import EmailIcon from '../assets/email-icon.svg';
import '../css/LoginPage.css'; // Aquí se incluirán los estilos
import Header from '../components/Header';

export default function LoginPage() {
  const [step, setStep] = useState(0);  // Estado para manejar los pasos
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [verificationCode, setVerificationCode] = useState('');
  const [error, setError] = useState('');

  // Validación del correo
  const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
    
  };

  // Validación del nombre
  const validateName = (name) => {
    return name.length > 0; // Verifica que el nombre no esté vacío
  };

  // Validación del código
  const validateCode = (code) => {
    return code.length === 6; // Suponiendo que el código tiene 6 dígitos
  };

  // Manejo del clic en el botón "Usar un correo electrónico"
  const handleEmailButtonClick = () => {
    setStep(1); // Mostrar el formulario de correo
  };

  // Manejo de la entrada del correo electrónico
  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  // Manejo del clic en "Continuar" - Paso 1
  const handleContinueEmail = () => {
    if (!email || !validateEmail(email)) {
      setError('Por favor, ingresa un correo válido.');
      return;
    }
    setError('');
    setStep(2); // Avanzar al paso 2 (nombre)
  };

  // Manejo de la entrada del nombre
  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  // Manejo del clic en "Continuar" - Paso 2
  const handleContinueName = () => {
    if (!name || !validateName(name)) {
      setError('Por favor, ingresa tu nombre.');
      return;
    }
    setError('');
    setStep(3); // Avanzar al paso 3 (verificación)
  };

  // Manejo de la entrada del código
  const handleCodeChange = (e) => {
    setVerificationCode(e.target.value);
  };

  // Manejo del clic en "Continuar" - Paso 3
  const handleContinueCode = () => {
    if (!verificationCode || !validateCode(verificationCode)) {
      setError('Por favor, ingresa un código válido.');
      return;
    }
    setError('');
    setStep(4); // Avanzar al paso 4 (redirección a perfil)
  };

  // Retroceder un paso
  const handleBack = () => {
    setStep(step - 1); // Retrocede un paso
  };

  return (
    <div>
      <Header/>
    
    <div className="login-container">
      
      <div className="login-card">
        {/* Paso 0: Mostrar título y botones de login */}
        {step === 0 && (
          <>
            <h1>Inicia sesión o regístrate en un momento</h1>
            <p>Usa tu correo electrónico o otro servicio para acceder a Kaimaki.</p>
            <div className="login-buttons">
              <GoogleLoginButton
                onSuccess={handleGoogleLogin}
                onError={handleGoogleError}
              />
              <button
                className="email-login-button"
                onClick={handleEmailButtonClick} // Cambiar al formulario de correo
              >
                <img src={EmailIcon} alt="Email icon" width="20" height="20" />
                Usar un correo electrónico
              </button>
            </div>
          </>
        )}

        {/* Paso 1: Formulario de correo electrónico */}
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

        {/* Paso 2: Formulario de nombre */}
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

        {/* Paso 3: Formulario de código de verificación */}
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

        {/* Paso 4: Redirigir al perfil */}
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
  );
}

// Funciones para el manejo de Google (simulación)
const handleGoogleLogin = async (response) => {
  console.log('Google login response', response);
  // Lógica de login con Google
};

const handleGoogleError = (error) => {
  console.error('Error en el login con Google:', error);
};
