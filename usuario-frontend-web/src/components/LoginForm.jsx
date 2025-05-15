// src/components/LoginForm.jsx
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'

const schema = yup.object({
  email: yup.string().email('Email inválido').required('Requerido'),
  password: yup.string().min(6, 'Mínimo 6 caracteres').required('Requerido'),
})

export function LoginForm({ onSubmit }) {
  const { register, handleSubmit, formState } = useForm({
    resolver: yupResolver(schema)
  })

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register('email')} placeholder="Email" />
      <p>{formState.errors.email?.message}</p>

      <input {...register('password')} type="password" placeholder="Contraseña" />
      <p>{formState.errors.password?.message}</p>

      <button type="submit">Entrar</button>
    </form>
  )
}
