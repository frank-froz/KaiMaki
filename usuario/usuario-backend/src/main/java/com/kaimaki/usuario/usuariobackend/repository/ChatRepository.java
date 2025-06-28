package com.kaimaki.usuario.usuariobackend.repository;

import com.kaimaki.usuario.usuariobackend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c JOIN c.participants p1 JOIN c.participants p2 " +
            "WHERE p1.id = :userId1 AND p2.id = :userId2 AND SIZE(c.participants) = 2")
    Optional<Chat> findChatBetween(Long userId1, Long userId2);

    @Query("SELECT c FROM Chat c JOIN c.participants p WHERE p.id = :userId")
    List<Chat> findAllByUserId(Long userId);

}

