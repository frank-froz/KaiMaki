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

    const onSubmit = async (data) => {
        try {
            // Mapeo de campos para backend
            const payload = {
                correo: data.email,
                contrasena: data.password,
            };

            // Llamada al endpoint con payload correcto
            const { data: res } = await apiRegister(payload);

            if (res.token) {
                login(res.token);
                navigate('/dashboard');
            } else {
                navigate('/login');
            }
        } catch (error) {
            console.error(error);
            // Aquí podrías mostrar un mensaje de error
        }
    };


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
