// src/components/RegisterForm.jsx
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'

const schema = yup.object({
  email: yup.string().email('Email inválido').required('Requerido'),
  password: yup.string().min(6, 'Mínimo 6 caracteres').required('Requerido'),
  confirmPassword: yup
    .string()
    .oneOf([yup.ref('password')], 'Las contraseñas no coinciden')
    .required('Requerido'),
})

export function RegisterForm({ onSubmit }) {
  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(schema)
  })

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register('email')} placeholder="Email" />
      <p>{formState.errors.email?.message}</p>

      <input
        {...register('password')}
        type="password"
        placeholder="Contraseña"
      />
      <p>{formState.errors.password?.message}</p>

      <input
        {...register('confirmPassword')}
        type="password"
        placeholder="Confirmar contraseña"
      />
      <p>{formState.errors.confirmPassword?.message}</p>

      <button type="submit">Registrarse</button>
    </form>
  )
}
