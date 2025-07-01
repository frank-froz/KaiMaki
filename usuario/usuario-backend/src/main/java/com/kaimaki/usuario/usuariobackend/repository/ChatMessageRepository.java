package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Chat;
import com.kaimaki.usuario.usuariobackend.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Obtener mensajes de un chat específico ordenados por fecha
    List<ChatMessage> findByChatOrderBySentAtAsc(Chat chat);

    // Obtener mensajes de un chat con paginación
    Page<ChatMessage> findByChatOrderBySentAtDesc(Chat chat, Pageable pageable);

    // Obtener últimos mensajes de un chat
    @Query("SELECT m FROM ChatMessage m WHERE m.chat = :chat ORDER BY m.sentAt DESC")
    List<ChatMessage> findLastMessagesByChat(@Param("chat") Chat chat, Pageable pageable);

    // Contar mensajes no leídos en un chat (si implementas lectura de mensajes más
    // adelante)
    @Query("SELECT COUNT(m) FROM ChatMessage m WHERE m.chat = :chat AND m.sentAt > :lastReadTime")
    Long countUnreadMessages(@Param("chat") Chat chat, @Param("lastReadTime") java.time.LocalDateTime lastReadTime);
}
