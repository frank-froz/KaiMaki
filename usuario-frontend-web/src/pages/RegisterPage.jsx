// src/pages/RegisterPage.jsx

import { useContext } from 'react'
import { useNavigate } from 'react-router-dom'
import { RegisterForm } from '../components/RegisterForm'
import { register as apiRegister } from '../services/authService'
import { AuthContext } from '../context/AuthContext'
import { SocialLoginButtons } from '../components/SocialLoginButtons'
import Header from '../components/Header';
import '../css/RegisterPage.css' // Aquí se incluirán los estilos;
export default function RegisterPage() {
  const { login } = useContext(AuthContext)
  const navigate = useNavigate()

  const onSubmit = async data => {
    try {
      // Llamada al endpoint de registro
      const { data: res } = await apiRegister({
        email: data.email,
        password: data.password
      })
      // Si el backend devuelve token, iniciamos sesión automáticamente
      if (res.token) {
        login(res.token)
        navigate('/dashboard')
      } else {
        // Si sólo confirma registro, llevamos al login
        navigate('/login')
      }
    } catch (error) {
      console.error(error)
      // Aquí podrías mostrar un toast o mensaje de error
    }
  }

  return (
      <div >
      <Header/>
      <div className="register-page">
      <h1>Registro</h1>
      <div className="register-form-container">
        <RegisterForm onSubmit={onSubmit} />
        <p>O regístrate con:</p>
        <SocialLoginButtons />
      </div>
     </div>
    </div>
  )
}
