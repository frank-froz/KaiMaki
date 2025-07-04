// src/pages/ChatPage.jsx
import { Client } from "@stomp/stompjs";
import { useContext, useEffect, useRef, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import SockJS from "sockjs-client";
import { AuthContext } from "../../context/AuthContext";
import api from "../../services/api";
import "../../styles/pages/ChatPage.css";

export default function ChatPage() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const { roomId } = useParams(); // Obtiene el ID del chat desde la URL (ej: /chat/user1_user2)

  // --- ESTADOS ---
  const [chats, setChats] = useState([]); // Lista de chats del usuario
  const [selectedChat, setSelectedChat] = useState(null); // El chat que está abierto
  const [messages, setMessages] = useState([]); // Mensajes del chat seleccionado
  const [input, setInput] = useState("");
  const [searchInput, setSearchInput] = useState(""); // Para buscar usuarios
  const [searchResults, setSearchResults] = useState([]); // Resultados de búsqueda
  const [isSearching, setIsSearching] = useState(false); // Estado de búsqueda
  const clientRef = useRef(null);
  const messagesEndRef = useRef(null); // Para hacer scroll automático

  // Función para hacer scroll al final de los mensajes
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  // Scroll automático cuando cambian los mensajes
  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  // --- EFECTO 1: Cargar la lista de chats del usuario al iniciar ---
  useEffect(() => {
    const fetchUserChats = async () => {
      if (!user) return;

      try {
        const token = localStorage.getItem("token");
        if (!token) {
          console.error("[ChatPage] No hay token para cargar chats");
          navigate("/login");
          return;
        }

        // Debug: Verificar información del usuario
        console.log("[ChatPage] Usuario actual:", user);
        console.log("[ChatPage] Token presente:", !!token);

        console.log("[ChatPage] Cargando chats del usuario...");

        // Antes:
        // const response = await api.get("/api/chat");
        // Después:
        const response = await api.get("/chat"); // El token se agrega automáticamente

        console.log("[ChatPage] Chats cargados exitosamente:", response.data);
        console.log("[ChatPage] Estructura del primer chat:", response.data[0]);
        setChats(response.data);
      } catch (error) {
        console.error("[ChatPage] Error al cargar la lista de chats:", error);
        if (error.response?.status === 403) {
          console.error(
            "[ChatPage] Error 403: Acceso denegado. Verificar rol de usuario."
          );
          console.error(
            "[ChatPage] Rol actual del usuario:",
            user?.rol || "No definido"
          );
        }
      }
    };
    fetchUserChats();
  }, [user]); // Se ejecuta cuando el usuario se carga

  // --- EFECTO 2: Cargar el historial de un chat cuando se selecciona o la URL cambia ---
  useEffect(() => {
    const fetchChatMessages = async (chatId) => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          console.error("[ChatPage] No hay token para cargar mensajes");
          return;
        }

        console.log("[ChatPage] Cargando mensajes del chat:", chatId);

        // Antes:
        // const response = await api.get(`/api/chat/${chatId}/messages`);
        // Después:
        const response = await api.get(`/chat/${chatId}/messages`);

        console.log("[ChatPage] Mensajes cargados:", response.data);
        setMessages(response.data);
      } catch (error) {
        console.error(
          "[ChatPage] Error al cargar los mensajes del chat:",
          error
        );
        if (error.response?.status === 403) {
          console.error("[ChatPage] Error 403: Acceso denegado al chat.");
        }
        setMessages([]); // Limpiar mensajes si hay error
      }
    };

    if (roomId) {
      fetchChatMessages(roomId);
      // También selecciona el chat correspondiente de la lista
      const chatFromList = chats.find((c) => c.roomId === roomId);
      if (chatFromList) {
        setSelectedChat(chatFromList);
      }
    } else {
      setSelectedChat(null);
      setMessages([]);
    }
  }, [roomId, chats]); // Se ejecuta cuando el roomId de la URL cambia o la lista de chats se carga

  // --- EFECTO 3: Conectar al WebSocket ---
  useEffect(() => {
    if (!user) {
      navigate("/login");
      return;
    }

    const token = localStorage.getItem("token");
    if (!token) {
      console.error("[ChatPage] No hay token, redirigiendo al login.");
      navigate("/login");
      return;
    }

    const client = new Client({
      webSocketFactory: () =>
        new SockJS("/ws-sockjs", null, { withCredentials: true }),
      reconnectDelay: 5000,
      connectHeaders: {
        authorization: `Bearer ${token}`,
      },
      onConnect: () => {
        console.log("[ChatPage] ✅ WebSocket conectado con autenticación");
        const currentUserEmail = user?.correo || user?.email;

        // Suscribirse a la cola personal del usuario (SIN el email en la ruta)
        client.subscribe(`/user/queue/messages`, (msg) => {
          const newMessage = JSON.parse(msg.body);
          console.log("¡Mensaje recibido del WebSocket!", newMessage);

          // Añadir el nuevo mensaje al estado
          setMessages((prevMessages) => {
            // Solo verificar duplicados para mensajes muy recientes (evitar duplicados del propio envío)
            const currentUserEmail = user?.correo || user?.email;
            const isMyMessage = newMessage.senderEmail === currentUserEmail;

            if (isMyMessage) {
              // Para mis propios mensajes, verificar si ya existe uno muy similar reciente
              const recentDuplicate = prevMessages.some(
                (m) =>
                  m.content === newMessage.content &&
                  m.senderEmail === newMessage.senderEmail &&
                  Math.abs(
                    new Date(m.timestamp || m.sentAt) -
                      new Date(newMessage.timestamp || newMessage.sentAt)
                  ) < 2000
              );

              if (recentDuplicate) {
                return prevMessages; // No agregar el duplicado
              }
            }

            // Siempre agregar mensajes de otros usuarios
            return [...prevMessages, newMessage];
          });
        });
      },
      onStompError: (frame) =>
        console.error("[ChatPage] Error de STOMP:", frame),
      onWebSocketError: (event) => {
        // Solo muestra el error si realmente la conexión no funciona
        if (event && event.type === "error") {
          // Puedes comentar la siguiente línea si no quieres ver el error
          // console.error("[ChatPage] Error de WebSocket:", event);
        } else {
          console.error("[ChatPage] Error de WebSocket:", event);
        }
      },
    });

    client.activate();
    clientRef.current = client;

    return () => {
      if (clientRef.current) {
        clientRef.current.deactivate();
      }
    };
  }, [user, navigate, roomId]); // Depende del usuario y del chat abierto (roomId)

  // --- FUNCIÓN PARA BUSCAR USUARIOS ---
  const searchUsers = async (email) => {
    if (!email.trim()) {
      setSearchResults([]);
      return;
    }

    setIsSearching(true);
    try {
      console.log("[ChatPage] Buscando usuarios con email:", email);
      const response = await api.get(
        `/api/usuarios/search?email=${encodeURIComponent(email)}`
      );
      console.log("[ChatPage] Usuarios encontrados:", response.data);
      setSearchResults(response.data);
    } catch (error) {
      console.error("[ChatPage] Error al buscar usuarios:", error);
      setSearchResults([]);
    } finally {
      setIsSearching(false);
    }
  };

  // --- FUNCIÓN PARA CREAR/ABRIR CHAT CON UN USUARIO ---
  const startChatWithUser = async (targetUser) => {
    try {
      const currentUserEmail = user?.correo || user?.email;
      console.log(
        "[ChatPage] Iniciando chat con:",
        targetUser.correo || targetUser.email
      );

      // Crear o obtener el chat
      // Antes:
      // const response = await api.post("/api/chat/start", {
      //   otherUserEmail: targetUser.correo || targetUser.email,
      // });
      // Después:
      const response = await api.post("/chat/start", {
        otherUserEmail: targetUser.correo || targetUser.email,
      });

      console.log("[ChatPage] Chat creado/obtenido:", response.data);

      // Actualizar la lista de chats
      // Antes:
      // const chatsResponse = await api.get("/api/chat");
      // Después:
      const chatsResponse = await api.get("/chat");
      setChats(chatsResponse.data);

      // Navegar al chat
      navigate(`/chat/${response.data.roomId}`);

      // Limpiar búsqueda
      setSearchInput("");
      setSearchResults([]);
    } catch (error) {
      console.error("[ChatPage] Error al crear/abrir chat:", error);
    }
  };

  // --- FUNCIÓN PARA ENVIAR MENSAJES ---
  const sendMessage = () => {
    if (!input || !selectedChat) return;

    const currentUserEmail = user?.correo || user?.email;
    // Buscar el otro participante usando la estructura real del ChatDTO
    const otherParticipant = selectedChat.participants?.find(
      (p) => p.email !== currentUserEmail
    );

    if (!otherParticipant) {
      console.error("[ChatPage] No se pudo encontrar el otro participante");
      return;
    }

    // El backend espera: { toEmail, content, roomId }
    const messageForBackend = {
      toEmail: otherParticipant.email,
      content: input,
      roomId: selectedChat.roomId,
    };

    console.log(
      "[ChatPage] Enviando mensaje por WebSocket:",
      messageForBackend
    );

    // IMPORTANTE: Agregar el mensaje localmente ANTES de enviarlo
    const tempMessage = {
      id: Date.now(), // ID temporal
      senderEmail: currentUserEmail,
      content: input,
      timestamp: new Date().toISOString(),
      sentAt: new Date().toISOString(),
    };

    setMessages((prevMessages) => [...prevMessages, tempMessage]);

    // CORRECCIÓN: Definir token aquí
    const token = localStorage.getItem("token");

    if (clientRef.current && clientRef.current.connected) {
      clientRef.current.publish({
        destination: "/app/chat",
        body: JSON.stringify(messageForBackend),
        headers: {
          authorization: `Bearer ${token}`, // <-- minúscula, igual que en CONNECT
        },
      });
      console.log("[ChatPage] ✅ Mensaje enviado por WebSocket");
    } else {
      console.error("[ChatPage] ❌ WebSocket no conectado");
    }
    setInput("");
  };

  // --- LÓGICA DE RENDERIZADO ---
  return (
    <div className="chat-whatsapp-layout">
      <div className="chat-sidebar">
        <div className="chat-sidebar-header">
          <div className="logo">
            <Link to="/">Kaimaki</Link>
          </div>
        </div>

        {/* Búsqueda de usuarios */}
        <div className="chat-search-container">
          <input
            type="text"
            placeholder="Buscar usuario por email..."
            value={searchInput}
            onChange={(e) => {
              setSearchInput(e.target.value);
              searchUsers(e.target.value);
            }}
            className="chat-search-input"
          />
          {isSearching && (
            <div className="chat-search-loading">Buscando...</div>
          )}

          {/* Resultados de búsqueda */}
          {searchResults.length > 0 && (
            <div className="chat-search-results">
              {searchResults.map((searchUser) => (
                <div
                  key={searchUser.id}
                  className="chat-search-result-item"
                  onClick={() => startChatWithUser(searchUser)}
                >
                  <span className="chat-sidebar-avatar">
                    {searchUser.nombre
                      ? searchUser.nombre[0].toUpperCase()
                      : (searchUser.correo ||
                          searchUser.email)[0].toUpperCase()}
                  </span>
                  <div className="chat-search-user-info">
                    <span className="chat-search-user-name">
                      {searchUser.nombre || "Sin nombre"}
                    </span>
                    <span className="chat-search-user-email">
                      {searchUser.correo || searchUser.email}
                    </span>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Lista de chats existentes */}
        <div className="chat-sidebar-list">
          {chats.map((chat) => {
            const currentUserEmail = user?.correo || user?.email;
            // Buscar el otro participante usando la estructura real del ChatDTO
            const otherParticipant = chat.participants?.find(
              (p) => p.email !== currentUserEmail
            );

            return (
              <div
                key={chat.roomId}
                className={`chat-sidebar-item${
                  selectedChat?.roomId === chat.roomId ? " selected" : ""
                }`}
                // Al hacer clic, navegamos a la URL del chat
                onClick={() => navigate(`/chat/${chat.roomId}`)}
              >
                <span className="chat-sidebar-avatar">
                  {otherParticipant?.nombre
                    ? otherParticipant.nombre[0].toUpperCase()
                    : "?"}
                </span>
                <span className="chat-sidebar-name">
                  {otherParticipant?.nombre ||
                    otherParticipant?.email ||
                    "Usuario Desconocido"}
                </span>
              </div>
            );
          })}
        </div>

        {/* Botón flotante de regreso al dashboard */}
        <button
          className="chat-back-button-floating"
          onClick={() => navigate("/dashboard")}
          title="Volver al Dashboard"
        >
          ←
        </button>
      </div>

      <div className="chat-main">
        <div className="chat-title">
          <div className="chat-header-left">
            {selectedChat && (
              <span className="chat-user-name">
                {selectedChat.participants?.find(
                  (p) => p.email !== (user?.correo || user?.email)
                )?.nombre || "Usuario"}
              </span>
            )}
          </div>
        </div>

        {selectedChat ? (
          <>
            <div className="chat-messages-box">
              {messages.map((msg, i) => {
                const isOwn = msg.senderEmail === (user?.correo || user?.email);
                return (
                  <div
                    key={i}
                    className={`chat-message-row ${
                      isOwn ? "chat-row-own" : "chat-row-other"
                    }`}
                  >
                    <div
                      className={`chat-message-bubble ${
                        isOwn ? "chat-own" : "chat-other"
                      }`}
                    >
                      <span className="chat-message-text">{msg.content}</span>
                      {/* Aquí podrías añadir un timestamp si lo tienes */}
                    </div>
                  </div>
                );
              })}
              {/* Elemento para hacer scroll automático */}
              <div ref={messagesEndRef} />
            </div>
            <div className="chat-input-row">
              <input
                type="text"
                placeholder="Escribe un mensaje..."
                value={input}
                onChange={(e) => setInput(e.target.value)}
                className="chat-input-msg"
                onKeyDown={(e) => e.key === "Enter" && sendMessage()}
              />
              <button onClick={sendMessage} className="chat-send-btn">
                Enviar
              </button>
            </div>
          </>
        ) : (
          <div className="chat-placeholder">
            Selecciona un chat para comenzar
          </div>
        )}
      </div>
    </div>
  );
}
