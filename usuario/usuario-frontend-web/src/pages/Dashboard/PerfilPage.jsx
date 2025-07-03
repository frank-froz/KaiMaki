// src/pages/dashboard/PerfilPage.jsx
import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { getPerfil, updatePerfil } from '../../services/perfilService';
import Perfil from '../../components/dashboard/Perfil';
import Header from '../../components/layout/Header';

const PerfilPage = () => {
  const { id: paramId } = useParams();
  const { user } = useContext(AuthContext);
  const [perfilData, setPerfilData] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPerfil = async () => {
      try {
        const response = await getPerfil(paramId);
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
      const res = await updatePerfil(perfilActualizado);
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
