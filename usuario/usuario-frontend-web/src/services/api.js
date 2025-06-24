import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080/', // URL base de tu backend
    headers: {
        'Content-Type': 'application/json',
    },
})

// Opcional: agregar interceptor para manejar errores globales o token si usas auth
api.interceptors.response.use(
    response => response,
    error => {
        // Puedes manejar errores comunes aquí, ej: expiración token, etc.
        return Promise.reject(error)
    }
)

export default api
