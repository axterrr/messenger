CREATE TABLE IF NOT EXISTS users (
    id          VARCHAR(36) PRIMARY KEY,
    phone       VARCHAR(15)  NOT NULL UNIQUE,
    email       VARCHAR(254) NOT NULL UNIQUE,
    name        VARCHAR(30)  NOT NULL,
    description VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS chat (
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(30) NULL
);

CREATE TABLE IF NOT EXISTS message (
     id        VARCHAR(36) PRIMARY KEY,
     content   VARCHAR(1000) NOT NULL,
     sent_at   TIMESTAMP     NOT NULL,
     chat_id   VARCHAR(36)   NOT NULL,
     sender_id VARCHAR(36)       NULL,
     CONSTRAINT fk_message_chat   FOREIGN KEY (chat_id)   REFERENCES chat(id)  ON DELETE CASCADE,
     CONSTRAINT fk_message_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS users_chats (
    user_id VARCHAR(36) NOT NULL,
    chat_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (user_id, chat_id),
    CONSTRAINT fk_users_chats_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_chats_chat FOREIGN KEY (chat_id) REFERENCES chat(id)  ON DELETE CASCADE
);
