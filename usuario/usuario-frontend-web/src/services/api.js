import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true
});

// Interceptor para agregar automáticamente el token en todas las peticiones
api.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

// Interceptor para manejar errores de respuesta
api.interceptors.response.use(
    response => response,
    error => {
        // Manejar errores comunes
        if (error.response?.status === 401) {
            console.error('[API] Token inválido o expirado');
            localStorage.removeItem('token');
            window.location.href = '/login';
        } else if (error.response?.status === 403) {
            console.error('[API] Acceso denegado - Error 403');
        }
        return Promise.reject(error);
    }
)

export default api