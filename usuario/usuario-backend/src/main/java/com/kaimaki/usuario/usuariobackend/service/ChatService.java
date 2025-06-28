package com.kaimaki.usuario.usuariobackend.service;

import com.kaimaki.usuario.usuariobackend.model.Chat;
import com.kaimaki.usuario.usuariobackend.model.ChatMessage;
import com.kaimaki.usuario.usuariobackend.model.Trabajador;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.ChatMessageRepository;
import com.kaimaki.usuario.usuariobackend.repository.ChatRepository;
import com.kaimaki.usuario.usuariobackend.repository.TrabajadorRepository;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository; // Cambio: usar directamente UserRepository

    public Chat crearOBuscarChat(Long userId, Long trabajadorId) {
        // 1. Buscar el trabajador para obtener su user_id
        Trabajador trabajador = trabajadorRepository.findById(trabajadorId)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        Long trabajadorUserId = trabajador.getUser().getId();

        // 2. Bloquear chat consigo mismo
        if (userId.equals(trabajadorUserId)) {
            throw new RuntimeException("No puedes crear un chat contigo mismo");
        }

        // 3. Verificar si ya existe un chat entre los dos USUARIOS
        Optional<Chat> chatExistente = chatRepository.findChatBetween(userId, trabajadorUserId);

        if (chatExistente.isPresent()) {
            return chatExistente.get();
        }

        // 4. Crear nuevo chat entre los dos USUARIOS
        Chat nuevoChat = new Chat();

        // Agregar participantes
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        User trabajadorUser = userRepository.findById(trabajadorUserId)
                .orElseThrow(() -> new RuntimeException("Trabajador usuario no encontrado"));

        List<User> participants = new ArrayList<>();
        participants.add(usuario);
        participants.add(trabajadorUser);
        nuevoChat.setParticipants(participants);

        return chatRepository.save(nuevoChat);
    }

    public ChatMessage enviarMensaje(Long emisorId, Long receptorId, String texto) {
        // 1. Buscar chat entre emisor y receptor
        Optional<Chat> chatOpt = chatRepository.findChatBetween(emisorId, receptorId);

        Chat chat;
        if (chatOpt.isPresent()) {
            chat = chatOpt.get();
        } else {
            // Crear nuevo chat si no existe
            chat = new Chat();

            // Agregar participantes
            User emisor = userRepository.findById(emisorId)
                    .orElseThrow(() -> new RuntimeException("Emisor no encontrado"));
            User receptor = userRepository.findById(receptorId)
                    .orElseThrow(() -> new RuntimeException("Receptor no encontrado"));

            List<User> participants = new ArrayList<>();
            participants.add(emisor);
            participants.add(receptor);
            chat.setParticipants(participants);

            chat = chatRepository.save(chat);
        }

        // 2. Crear el mensaje
        ChatMessage mensaje = new ChatMessage();
        mensaje.setChat(chat);
        mensaje.setFrom(userRepository.findById(emisorId)
                .orElseThrow(() -> new RuntimeException("Emisor no encontrado")));
        mensaje.setTo(userRepository.findById(receptorId)
                .orElseThrow(() -> new RuntimeException("Receptor no encontrado")));
        mensaje.setText(texto);
        mensaje.setSentAt(LocalDateTime.now());
        mensaje.setLeido(false);

        return chatMessageRepository.save(mensaje);
    }

    public List<ChatMessage> obtenerMensajesDeChat(Long chatId) {
        return chatMessageRepository.findByChatIdOrderBySentAtAsc(chatId);
    }

    public List<Chat> obtenerChatsDeUsuario(Long userId) {
        return chatRepository.findAllByUserId(userId);
    }
}