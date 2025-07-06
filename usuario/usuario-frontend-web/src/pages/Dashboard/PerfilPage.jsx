// src/pages/dashboard/PerfilPage.jsx
import React, { useContext, useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from '../../context/AuthContext';
import { getPerfil, updatePerfil } from '../../services/perfilService';
import { getTrabajadoresDisponibles } from '../../services/trabajadorService';
import Perfil from '../../components/dashboard/Perfil';
import Header from '../../components/layout/Header';
import TrabajadoresList from '../../components/trabajadores/TrabajadoresList';
import api from '../../services/api';

const PerfilPage = () => {
  const { id: paramId } = useParams();
  const { user } = useContext(AuthContext);
  const [perfilData, setPerfilData] = useState(null);
  const [error, setError] = useState(null);
  const [trabajadores, setTrabajadores] = useState([]);
  const navigate = useNavigate();

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

  useEffect(() => {
    const fetchTrabajadores = async () => {
      try {
        const response = await getTrabajadoresDisponibles();
        setTrabajadores(response.data);
      } catch (err) {
        console.error('Error cargando trabajadores:', err);
      }
    };
    fetchTrabajadores();
  }, []);

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

  // Función para crear/abrir chat con el usuario del perfil
  const handleMessageClick = async (perfil) => {
    try {
      // El backend espera el email del otro usuario
      const otherUserEmail = perfil.correo || perfil.email;
      const response = await api.post('/chat/start', { otherUserEmail });
      // Navegar al chat
      navigate(`/chat/${response.data.roomId}`);
    } catch (error) {
      alert('No se pudo iniciar el chat.');
      console.error('Error al crear/abrir chat:', error);
    }
  };

  if (error) return <p className="text-red-500 text-center mt-10">{error}</p>;
  if (!perfilData) return <p className="text-center mt-10">Cargando perfil...</p>;

  const editable = !paramId || paramId === String(user?.id);

  return (
    <div>
      <Header />
      <div className="p-6">
        <Perfil data={perfilData} editable={editable} onSave={handleSavePerfil} onMessageClick={handleMessageClick} />
      </div>
      {/* Sección Más trabajadores solo si NO es el perfil propio */}
      {!editable && (
        <div className="mas-trabajadores-section">
          <h2 className="mas-trabajadores-title">Más trabajadores</h2>
          <TrabajadoresList
            trabajadores={trabajadores
              .filter(t => t.userId !== perfilData?.userId)
              .slice(0, 6)
            }
          />
        </div>
      )}
    </div>
  );
};

export default PerfilPage;
