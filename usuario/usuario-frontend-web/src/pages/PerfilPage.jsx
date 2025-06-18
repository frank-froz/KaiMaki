import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../context/AuthContext';
import { perfil } from '../services/authService';
import Perfil from '../components/Perfil';
import Header from '../components/Header';

const PerfilPage = () => {
  const { user } = useContext(AuthContext);
  const [perfilData, setPerfilData] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      setError("Token no encontrado. Inicia sesión.");
      return;
    }

    const fetchPerfil = async () => {
      try {
        const response = await perfil(token);
        setPerfilData(response.data);
      } catch (err) {
        console.error("Error al obtener perfil:", err);
        setError("No se pudo cargar el perfil.");
      }
    };

    fetchPerfil();
  }, []);

  if (error) return <p className="text-red-500 text-center mt-10">{error}</p>;
  if (!perfilData) return <p className="text-center mt-10">Cargando perfil...</p>;

  return (
      <div>
        <Header />
        <div className="p-6">
          <Perfil data={perfilData} editable={true} />
        </div>
      </div>
  );
};

export default PerfilPage;
