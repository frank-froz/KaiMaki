import axios from 'axios'

const api = axios.create({
  baseURL: '/api',  // URL relativa para que el proxy funcione
})

export const register = data => api.post('/usuarios/registro', data)


export const login = data =>
    api.post('/auth/login', data)

export const oauthGoogle = idToken =>
    api.post('/auth/oauth/google', { idToken })
