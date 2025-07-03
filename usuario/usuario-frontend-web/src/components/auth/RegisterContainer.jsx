import { useState } from 'react'
import { register } from '../../services/authService.js'  // usa la función importada
import { RegisterForm } from './RegisterForm.jsx'

export function RegisterContainer() {
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState(null)

    const onSubmit = async (data) => {
        console.log('onSubmit en el padre con datos:', data);
        setLoading(true)
        setError(null)

        try {
            const payload = {
                correo: data.email,
                contrasena: data.password,
            }

            console.log('Payload que se enviará al backend:', payload)
            const response = await register(payload)

            if (response.status === 200) {
                alert('Registro exitoso')
            }
        } catch (err) {
            // Aquí capturamos el error detallado que viene del backend
            if (err.response && err.response.data) {
                console.error('Error backend:', err.response.data)
                setError(typeof err.response.data === 'string' ? err.response.data : JSON.stringify(err.response.data))
            } else {
                setError('Error al registrar usuario')
            }
        } finally {
            setLoading(false)
        }
    }


    return (
        <div>
            {loading && <p>Registrando...</p>}
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <RegisterForm onSubmit={onSubmit} />
        </div>
    )
}
