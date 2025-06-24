import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
    withCredentials: true, // necesario para cookies o tokens que se envían por header o cookie
});
export const getTrabajadoresDisponibles = () => api.get('/trabajadores/disponibles');
