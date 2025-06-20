import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true  // necesario para cookies o tokens que se envían por header o cookie
})
export const register = data => api.post('/usuarios/registro', data)

export const login = data => api.post('/usuarios/login', data)

export const oauthGoogle = idToken => api.post('/auth/google', { idToken })

export const perfil = (token) =>
    api.get('/perfil', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

