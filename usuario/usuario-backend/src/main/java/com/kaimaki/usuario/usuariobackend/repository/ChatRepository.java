package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    // Buscar chat por roomId
    Optional<Chat> findByRoomId(String roomId);

    // Buscar chats donde el usuario participa
    @Query("SELECT c FROM Chat c JOIN c.participants p WHERE p.correo = :email ORDER BY c.updatedAt DESC")
    List<Chat> findChatsByUserEmail(@Param("email") String email);

    // Verificar si existe un chat entre dos usuarios
    @Query("SELECT c FROM Chat c JOIN c.participants p1 JOIN c.participants p2 " +
            "WHERE p1.correo = :email1 AND p2.correo = :email2 AND p1 != p2")
    Optional<Chat> findChatBetweenUsers(@Param("email1") String email1, @Param("email2") String email2);

    // Obtener chats con mensajes recientes
    @Query("SELECT DISTINCT c FROM Chat c LEFT JOIN FETCH c.messages m " +
            "JOIN c.participants p WHERE p.correo = :email " +
            "ORDER BY c.updatedAt DESC")
    List<Chat> findChatsWithRecentMessages(@Param("email") String email);
}
