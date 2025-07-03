// src/components/RegisterForm.jsx
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'

// Validación con Yup
const schema = yup.object({
    email: yup
        .string()
        .email('Email inválido')
        .matches(/@tecsup\.edu\.pe$/, 'Solo se permite correo institucional @tecsup.edu.pe')
        .required('Requerido'),

    password: yup
        .string()
        .min(6, 'Mínimo 6 caracteres')
        .required('Requerido'),

    confirmPassword: yup
        .string()
        .oneOf([yup.ref('password')], 'Las contraseñas no coinciden')
        .required('Requerido'),
})

export function RegisterForm({ onSubmit }) {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        resolver: yupResolver(schema),
    })

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <input
                {...register('email')}
                placeholder="Correo institucional"
                type="email"
            />
            <p>{errors.email?.message}</p>

            <input
                {...register('password')}
                placeholder="Contraseña"
                type="password"
            />
            <p>{errors.password?.message}</p>

            <input
                {...register('confirmPassword')}
                placeholder="Confirmar contraseña"
                type="password"
            />
            <p>{errors.confirmPassword?.message}</p>

            <button type="submit">Registrarse</button>
        </form>
    )
}
