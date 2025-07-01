import api from './api';

export const getPerfil = (paramId) => {
  return paramId ? api.get(`/perfil/${paramId}`) : api.get('/perfil');
};

export const updatePerfil = (perfilActualizado) => {
  return api.put('/perfil', perfilActualizado);
};
