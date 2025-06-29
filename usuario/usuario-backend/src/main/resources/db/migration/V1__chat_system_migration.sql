-- Migración segura y simplificada para el sistema de chat mejorado
-- Este script es compatible con datos existentes y más simple

-- 1. Crear tabla chats si no existe (versión mejorada)
CREATE TABLE IF NOT EXISTS chats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    room_id VARCHAR(500) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_room_id (room_id),
    INDEX idx_updated_at (updated_at)
);

-- 2. Crear tabla chat_participants si no existe
CREATE TABLE IF NOT EXISTS chat_participants (
    chat_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (chat_id, user_id),
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id)
);

-- 3. Crear tabla chat_messages con la nueva estructura
CREATE TABLE IF NOT EXISTS chat_messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_email VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    chat_id BIGINT NOT NULL,
    sender_id BIGINT NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_chat_id (chat_id),
    INDEX idx_sender_id (sender_id),
    INDEX idx_sent_at (sent_at)
);

-- 4. Crear algunos datos de ejemplo para pruebas (OPCIONAL - comentar en producción)
-- INSERT IGNORE INTO chats (room_id, created_at, updated_at) 
-- VALUES ('user1@tecsup.edu.pe_user2@tecsup.edu.pe', NOW(), NOW());

-- Nota: Para migrar datos existentes de chat_message a chat_messages,
-- se puede hacer manualmente después de verificar la estructura de la tabla anterior.
