import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
    withCredentials: true, // necesario para cookies o tokens que se envían por header o cookie
});
export const getTrabajadoresDisponibles = () => {
  const token = localStorage.getItem("token");

  return api.get('/trabajadores/disponibles', {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
};
