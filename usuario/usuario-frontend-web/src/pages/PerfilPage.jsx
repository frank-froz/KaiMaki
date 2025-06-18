import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Perfil from '../components/Perfil';
import { AuthContext } from '../context/AuthContext';
import Header from '../components/Header';

const PerfilPage = () => {
  const { user } = useContext(AuthContext);
  const { id: paramId } = useParams();
  const id = paramId || user?.id;

  const [perfil, setPerfil] = useState(null);

  useEffect(() => {
    console.log("ID desde contexto:", user?.id);
    console.log("ID desde URL:", paramId);

    if (!id) return;

    const fetchPerfil = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/perfil/${id}`);
        setPerfil(response.data);
      } catch (error) {
        console.error("Error al cargar perfil:", error);
      }
    };

    fetchPerfil();
  }, [id, paramId, user?.id]);


  if (!perfil) return <p className="text-center mt-10">Cargando perfil...</p>;

  return (
    <div>
      <Header />
      <div className="p-6">
        <Perfil data={perfil} editable={id == user?.id} />
      </div>
    </div>
  );
};

export default PerfilPage;
