// src/pages/ChatPage.jsx
import { useContext, useEffect, useRef, useState } from 'react'
import { AuthContext } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import { Client } from '@stomp/stompjs'
import './ChatPage.css'

export default function ChatPage() {
  const { user } = useContext(AuthContext)
  const navigate = useNavigate()
  const [messages, setMessages] = useState([])
  const [input, setInput] = useState('')
  const [to, setTo] = useState('')
  const [search, setSearch] = useState('')
  // Simulación de chats recientes/buscados
  const [chats, setChats] = useState([]) // Inicia vacío, sin chats por defecto
  const [selectedChat, setSelectedChat] = useState(null)
  const clientRef = useRef(null)

  useEffect(() => {
    if (!user) {
      navigate('/login')
      return
    }
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      reconnectDelay: 5000,
      onConnect: () => {
        client.subscribe('/topic/messages', (msg) => {
          const body = JSON.parse(msg.body)
          setMessages((prev) => [...prev, body])
        })
      },
    })
    client.activate()
    clientRef.current = client
    return () => client.deactivate()
  }, [user, navigate])

  const sendMessage = () => {
    if (!input || !selectedChat) return
    const message = { from: user?.email || user?.sub, to: selectedChat.name, text: input }
    // Mostrar el mensaje propio inmediatamente
    setMessages((prev) => [...prev, message])
    clientRef.current.publish({
      destination: '/app/chat',
      body: JSON.stringify(message),
    })
    setInput('')
  }

  // Buscar usuario por correo
  const handleSearch = () => {
    if (!search || chats.some(c => c.name === search)) return
    const newChat = { id: Date.now(), name: search }
    setChats(prev => [...prev, newChat])
    setSelectedChat(newChat)
    setTo(search)
    setSearch('')
  }

  // Filtrar mensajes solo del chat seleccionado
  const filteredMessages = selectedChat
    ? messages.filter(m => (m.from === (user?.email || user?.sub) && m.to === selectedChat.name) || (m.from === selectedChat.name && m.to === (user?.email || user?.sub)))
    : []

  return (
    <div className="chat-whatsapp-layout">
      <div className="chat-sidebar">
        <h3 className="chat-sidebar-title">Chats</h3>
        <div className="chat-search-row">
          <input
            type="text"
            placeholder="Buscar por correo..."
            value={search}
            onChange={e => setSearch(e.target.value)}
            className="chat-input-user"
            onKeyDown={e => e.key === 'Enter' && handleSearch()}
          />
          <button className="chat-search-btn" onClick={handleSearch}>Buscar</button>
        </div>
        <div className="chat-sidebar-list">
          {chats.map(chat => (
            <div
              key={chat.id}
              className={`chat-sidebar-item${selectedChat && selectedChat.id === chat.id ? ' selected' : ''}`}
              onClick={() => { setSelectedChat(chat); setTo(chat.name) }}
            >
              <span className="chat-sidebar-avatar">{chat.name[0].toUpperCase()}</span>
              <span className="chat-sidebar-name">{chat.name}</span>
            </div>
          ))}
        </div>
      </div>
      <div className="chat-main">
        <h2 className="chat-title">Chat con {selectedChat ? selectedChat.name : '...'}</h2>
        <div className="chat-messages-box">
          {filteredMessages.map((msg, i) => {
            const isOwn = msg.from === (user?.email || user?.sub)
            return (
              <div
                key={i}
                className={`chat-message-row ${isOwn ? 'chat-row-own' : 'chat-row-other'}`}
              >
                <div className={`chat-message-bubble ${isOwn ? 'chat-own' : 'chat-other'}`}>  
                  <span className="chat-message-text">{msg.text}</span>
                  <span className="chat-message-meta">
                    {isOwn ? 'Tú' : msg.from}
                  </span>
                </div>
              </div>
            )
          })}
        </div>
        <div className="chat-input-row">
          <input
            type="text"
            placeholder="Escribe un mensaje..."
            value={input}
            onChange={e => setInput(e.target.value)}
            className="chat-input-msg"
            onKeyDown={e => e.key === 'Enter' && sendMessage()}
            disabled={!selectedChat}
          />
          <button onClick={sendMessage} className="chat-send-btn" disabled={!selectedChat}>Enviar</button>
        </div>
      </div>
    </div>
  )
}
