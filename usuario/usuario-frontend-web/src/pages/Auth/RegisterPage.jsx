// src/pages/RegisterPage.jsx
import { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { RegisterForm } from '../../components/auth/RegisterForm'
import { register as apiRegister } from '../../services/authService'
import { AuthContext } from '../../context/AuthContext'
import { SocialLoginButtons } from '../../components/auth/SocialLoginButtons'
import Header from '../../components/layout/Header'
import '../../styles/pages/RegisterPage.css'

export default function RegisterPage() {
    const { login } = useContext(AuthContext)
    const navigate = useNavigate()
    const [errorMsg, setErrorMsg] = useState('')

    const onSubmit = async (data) => {
        try {
            const payload = {
                correo: data.email, // nombre esperado por el backend
                contrasena: data.password,
            }

            const res = await apiRegister(payload)
            console.log('Respuesta del backend:', res.data)

            if (res.data.token) {
                login(res.data.token)
                navigate('/dashboard')
            } else {
                setErrorMsg('No se recibió un token. Intenta iniciar sesión.')
                navigate('/login')
            }
        } catch (error) {
            console.error(error)
            setErrorMsg(error.response?.data || 'Error en el registro')
        }
    }

    return (
        <div>
            <Header />
            <div className="register-page">
                <h1>Registro</h1>
                <div className="register-form-container">
                    {errorMsg && <p className="error">{errorMsg}</p>}
                    <RegisterForm onSubmit={onSubmit} />
                    <p>O regístrate con:</p>
                    <SocialLoginButtons />
                </div>
            </div>
        </div>
    )
}
