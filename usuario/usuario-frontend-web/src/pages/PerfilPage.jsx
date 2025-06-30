import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';
import Perfil from '../components/Perfil';
import Header from '../components/Header';

const PerfilPage = () => {
  const { id: paramId } = useParams();
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

  const handleSavePerfil = async (perfilActualizado) => {
    try {
      const token = localStorage.getItem("token");
      const res = await axios.put("http://localhost:8080/api/perfil", perfilActualizado, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setPerfilData(res.data);
      alert("Perfil actualizado con éxito");
    } catch (err) {
      console.error("Error al actualizar el perfil:", err);
      alert("Hubo un error al guardar los cambios");
    }
  };

  if (error) return <p className="text-red-500 text-center mt-10">{error}</p>;
  if (!perfilData) return <p className="text-center mt-10">Cargando perfil...</p>;

  const editable = !paramId || paramId === String(user?.id);

  return (
    <div>
      <Header />
      <div className="p-6">
        <Perfil data={perfilData} editable={editable} onSave={handleSavePerfil} />
      </div>
    </div>
  );
};

export default PerfilPage;
