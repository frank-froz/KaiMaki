import api from './api';

export const register = (data) => api.post('/usuarios/registro', data);

export const login = (data) => api.post('/usuarios/login', data);

export const oauthGoogle = (idToken) => api.post('/auth/google', { idToken });

export const perfil = () => api.get('/perfil');
