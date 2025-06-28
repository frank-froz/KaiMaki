package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.ChatDTO;
import com.kaimaki.usuario.usuariobackend.model.ChatMessage;

import com.kaimaki.usuario.usuariobackend.dto.ChatMessageDTO;
import com.kaimaki.usuario.usuariobackend.model.Chat;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.service.ChatService;
import com.kaimaki.usuario.usuariobackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/chat")
@PreAuthorize("hasAnyRole('CLIENTE', 'TRABAJADOR')")
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    // NUEVO ENDPOINT: Crear/buscar chat con trabajador
    @PostMapping("/trabajador/{trabajadorId}")
    public ResponseEntity<?> crearChatConTrabajador(@PathVariable Long trabajadorId) {
        try {
            Long userId = userService.obtenerUsuarioActual().getId();

            // Log para debug
            logger.info("Usuario {} creando chat con trabajador {}", userId, trabajadorId);

            Chat chat = chatService.crearOBuscarChat(userId, trabajadorId);
            ChatDTO chatDTO = new ChatDTO(chat, userService.obtenerUsuarioActual());

            return ResponseEntity.ok(chatDTO);

        } catch (RuntimeException e) {
            logger.error("Error al crear chat: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{receptorId}/mensaje")
    public ResponseEntity<?> enviarMensaje(@PathVariable Long receptorId, @RequestBody Map<String, String> payload) {

        // Log para debug (temporal)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario: {}, Roles: {}, ReceptorId: {}",
                auth.getName(), auth.getAuthorities(), receptorId);

        Long emisorId = userService.obtenerUsuarioActual().getId();
        String texto = payload.get("texto");

        ChatMessage mensaje = chatService.enviarMensaje(emisorId, receptorId, texto);
        ChatMessageDTO dto = new ChatMessageDTO(mensaje);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{chatId}/mensajes")
    public ResponseEntity<?> obtenerMensajes(@PathVariable Long chatId) {
        List<ChatMessage> mensajes = chatService.obtenerMensajesDeChat(chatId);
        List<ChatMessageDTO> respuesta = mensajes.stream()
                .map(ChatMessageDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping
    public ResponseEntity<?> obtenerMisChats() {
        Long userId = userService.obtenerUsuarioActual().getId();
        User actual = userService.obtenerUsuarioActual();
        List<Chat> chats = chatService.obtenerChatsDeUsuario(userId);

        List<ChatDTO> resultado = chats.stream()
                .map(chat -> new ChatDTO(chat, actual))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }
}