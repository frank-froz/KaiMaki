// src/services/authService.js
import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL
})

export const register = data =>
  api.post('/auth/register', data)

export const login = data =>
  api.post('/auth/login', data)

export const oauthGoogle = idToken =>
  api.post('/auth/oauth/google', { idToken })

export const oauthFacebook = accessToken =>
  api.post('/auth/oauth/facebook', { accessToken })
