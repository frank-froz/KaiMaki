// services/trabajadorService.js
import api from './api';

export const getTrabajadoresDisponibles = () => {
  return api.get('/trabajadores/disponibles');
};
