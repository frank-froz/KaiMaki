import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import Perfil from '../components/Perfil';
import Header from '../components/Header';

const PerfilPage = () => {
  const { id: paramId } = useParams(); // Puede ser undefined si es tu propio perfil
  const { user } = useContext(AuthContext);
  const [perfilData, setPerfilData] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPerfil = async () => {
      try {
        let response;

        if (paramId) {
          response = await axios.get(`http://localhost:8080/api/perfil/${paramId}`);
        } else {
          const token = localStorage.getItem("token");
          console.log("Token:", token);
          response = await axios.get("http://localhost:8080/api/perfil", {
            headers: { Authorization: `Bearer ${token}` }
          });
        }

        setPerfilData(response.data);
      } catch (err) {
        console.error("Error cargando perfil:", err);
        setError("No se pudo cargar el perfil.");
      }
    };

    fetchPerfil();
  }, [paramId]);

  if (error) return <p className="text-red-500 text-center mt-10">{error}</p>;
  if (!perfilData) return <p className="text-center mt-10">Cargando perfil...</p>;

  // Editable solo si no hay paramId o si paramId == user.id
  const editable = !paramId || paramId === String(user?.id);

  return (
    <div>
      <Header />
      <div className="p-6">
        <Perfil data={perfilData} editable={editable} />
      </div>
    </div>
  );
};

export default PerfilPage;
