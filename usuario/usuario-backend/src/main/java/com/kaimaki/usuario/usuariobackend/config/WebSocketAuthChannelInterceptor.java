package com.kaimaki.usuario.usuariobackend.config;

import com.kaimaki.usuario.usuariobackend.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class WebSocketAuthChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        System.out.println("🔍 [WebSocketAuth] Comando recibido: " + accessor.getCommand());
        System.out.println("🔍 [WebSocketAuth] Usuario actual: " + accessor.getUser());
        System.out.println("🔍 [WebSocketAuth] Headers: " + accessor.toNativeHeaderMap());

        // Procesar autenticación en cualquier comando si el usuario es null y hay header Authorization
        if (accessor.getUser() == null) {
            processAuthentication(accessor);
        }

        // ¡IMPORTANTE! Devuelve el mensaje con los headers actualizados
        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }

    private void processAuthentication(StompHeaderAccessor accessor) {
        List<String> authHeader = accessor.getNativeHeader("authorization"); // <-- minúscula
        System.out.println("🔍 [WebSocketAuth] Authorization headers: " + authHeader);

        if (authHeader != null && !authHeader.isEmpty()) {
            String token = authHeader.get(0);
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            System.out.println("🔑 [WebSocketAuth] Token extraído: " + token.substring(0, Math.min(50, token.length())) + "...");

            try {
                if (jwtService.validateToken(token)) {
                    String correo = jwtService.getCorreoFromToken(token);
                    String rol = jwtService.getRolFromToken(token);

                    System.out.println("✅ [WebSocketAuth] Token válido para: " + correo + " con rol: " + rol);

                    // Crear Principal simple
                    Principal principal = new Principal() {
                        @Override
                        public String getName() {
                            return correo;
                        }
                    };

                    accessor.setUser(principal);
                    System.out.println("🎯 [WebSocketAuth] Principal establecido correctamente: " + correo);
                } else {
                    System.out.println("❌ [WebSocketAuth] Token inválido");
                }
            } catch (Exception e) {
                System.out.println("❌ [WebSocketAuth] Error validando token: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ [WebSocketAuth] No se encontró header authorization");
        }
    }
}