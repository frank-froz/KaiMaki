import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  withCredentials: true  // necesario para cookies o tokens que se envían por header o cookie
})

// Interceptor para agregar automáticamente el token de localStorage
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
export const register = data => api.post('/usuarios/registro', data)

export const login = data => api.post('/usuarios/login', data)

export const oauthGoogle = idToken => api.post('/auth/google', { idToken })

export const perfil = (token) =>
    api.get('/perfil', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });