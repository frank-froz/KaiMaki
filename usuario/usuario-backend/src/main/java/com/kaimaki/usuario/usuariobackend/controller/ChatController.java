package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.ChatDTO;
import com.kaimaki.usuario.usuariobackend.dto.ChatMessageDTO;
import com.kaimaki.usuario.usuariobackend.dto.SendMessageRequestDTO;
import com.kaimaki.usuario.usuariobackend.dto.StartChatRequestDTO;
import com.kaimaki.usuario.usuariobackend.dto.WebSocketMessageDTO;
import com.kaimaki.usuario.usuariobackend.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // ========== WebSocket Endpoints ==========

    @MessageMapping("/chat")
    public void send(WebSocketMessageDTO message) {
        System.out.println("Mensaje WebSocket recibido: " + message);
        System.out.println("Sender email: " + message.getSenderEmail());
        System.out.println("To email: " + message.getToEmail());

        // Validar que el mensaje tenga los datos necesarios
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            System.err.println("Error: Mensaje vacío");
            return;
        }

        if (message.getSenderEmail() == null || message.getSenderEmail().trim().isEmpty()) {
            System.err.println("Error: Email del remitente vacío");
            return;
        }
        if (message.getToEmail() == null || message.getToEmail().trim().isEmpty()) {
            System.err.println("Error: Email del destinatario vacío");
            return;
        } // Paso 1: Persistir el mensaje usando el servicio (PRIMERO GUARDAMOS)
        try {
            String fromEmail = message.getSenderEmail();
            String toEmail = message.getToEmail();

            System.out.println("Intentando guardar mensaje de " + fromEmail + " a " + toEmail);

            // Generar roomId
            String roomId = com.kaimaki.usuario.usuariobackend.model.Chat.generateRoomId(fromEmail, toEmail);
            System.out.println("RoomId generado: " + roomId);

            // Crear el chat si no existe (IMPORTANTE: Esto debe hacerse antes de enviar el
            // mensaje)
            ChatDTO chat = chatService.getOrCreateChat(fromEmail, toEmail);
            System.out.println("Chat creado/encontrado: " + chat.getRoomId());

            // Guardar el mensaje en la base de datos
            ChatMessageDTO savedMessage = chatService.sendMessage(roomId, fromEmail, message.getContent());

            System.out.println("Mensaje guardado correctamente con ID: " + savedMessage.getId());

            // Paso 2: Envío al destinatario (CARTERO PRIVADO)
            System.out.println("Enviando mensaje al destinatario: " + toEmail);
            messagingTemplate.convertAndSendToUser(
                    toEmail, // Email del usuario que recibe
                    "/queue/messages", // Cola privada donde escucha
                    savedMessage // El mensaje guardado
            );

            // Paso 3: Envío de confirmación al remitente (CONFIRMACIÓN)
            System.out.println("Enviando confirmación al remitente: " + fromEmail);
            messagingTemplate.convertAndSendToUser(
                    fromEmail, // Email del usuario que envió
                    "/queue/messages", // Su cola privada
                    savedMessage // El mensaje guardado
            );

            System.out.println("Mensaje enviado exitosamente a ambos usuarios");

        } catch (Exception e) {
            System.err.println("Error al procesar mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========== REST Endpoints ==========

    /**
     * Obtener chats del usuario autenticado
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE', 'TRABAJADOR')")
    @ResponseBody
    public ResponseEntity<List<ChatDTO>> getUserChats(Authentication authentication) {
        String userEmail = authentication.getName();
        List<ChatDTO> chats = chatService.getUserChats(userEmail);
        return ResponseEntity.ok(chats);
    }

    /**
     * Obtener o crear un chat con otro usuario
     */
    @PostMapping("/start")
    @PreAuthorize("hasAnyRole('CLIENTE', 'TRABAJADOR')")
    @ResponseBody
    public ResponseEntity<ChatDTO> startChat(
            @RequestBody @Valid StartChatRequestDTO request,
            Authentication authentication) {

        String currentUserEmail = authentication.getName();
        String otherUserEmail = request.getOtherUserEmail();

        if (currentUserEmail.equals(otherUserEmail)) {
            return ResponseEntity.badRequest().build(); // No puede chatear consigo mismo
        }

        try {
            ChatDTO chat = chatService.getOrCreateChat(currentUserEmail, otherUserEmail);
            return ResponseEntity.ok(chat);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtener mensajes de un chat específico
     */
    @GetMapping("/{roomId}/messages")
    @PreAuthorize("hasAnyRole('CLIENTE', 'TRABAJADOR')")
    @ResponseBody
    public ResponseEntity<List<ChatMessageDTO>> getChatMessages(
            @PathVariable String roomId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        try {
            List<ChatMessageDTO> messages = chatService.getChatMessages(roomId, userEmail);
            return ResponseEntity.ok(messages);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Enviar un mensaje a un chat
     */
    @PostMapping("/{roomId}/messages")
    @PreAuthorize("hasAnyRole('CLIENTE', 'TRABAJADOR')")
    @ResponseBody
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @PathVariable String roomId,
            @RequestBody @Valid SendMessageRequestDTO request,
            Authentication authentication) {

        String senderEmail = authentication.getName();
        String content = request.getContent();

        try {
            ChatMessageDTO message = chatService.sendMessage(roomId, senderEmail, content);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Obtener información de un chat específico
     */
    @GetMapping("/{roomId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'TRABAJADOR')")
    @ResponseBody
    public ResponseEntity<ChatDTO> getChatInfo(
            @PathVariable String roomId,
            Authentication authentication) {

        String userEmail = authentication.getName();

        try {
            ChatDTO chat = chatService.getChatByRoomId(roomId, userEmail);
            return ResponseEntity.ok(chat);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint de debugging para verificar conectividad
     * TEMPORAL - Remover en producción
     */
    @GetMapping("/debug/test")
    @PreAuthorize("hasAnyRole('CLIENTE', 'TRABAJADOR')")
    @ResponseBody
    public ResponseEntity<String> debugTest(Authentication authentication) {
        try {
            String userEmail = authentication.getName();
            System.out.println("[DEBUG] Usuario autenticado: " + userEmail);

            // Verificar que el usuario existe en la BD
            List<ChatDTO> chats = chatService.getUserChats(userEmail);
            System.out.println("[DEBUG] Chats del usuario: " + chats.size());

            return ResponseEntity.ok("✅ Debug exitoso. Usuario: " + userEmail + ", Chats: " + chats.size());
        } catch (Exception e) {
            System.err.println("[DEBUG] Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error: " + e.getMessage());
        }
    }
}
