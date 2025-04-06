CREATE TABLE IF NOT EXISTS users (
    id          UUID PRIMARY KEY,
    phone       VARCHAR(15)  NOT NULL UNIQUE,
    email       VARCHAR(254) NOT NULL UNIQUE,
    name        VARCHAR(30)  NOT NULL,
    description VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS chat (
    id   UUID PRIMARY KEY,
    name VARCHAR(30) NULL
);

CREATE TABLE IF NOT EXISTS message (
     id        UUID PRIMARY KEY,
     content   VARCHAR(1000) NOT NULL,
     sent_at   TIMESTAMP     NOT NULL,
     chat_id   UUID          NOT NULL,
     sender_id UUID              NULL,
     CONSTRAINT fk_message_chat   FOREIGN KEY (chat_id)   REFERENCES chat(id)  ON DELETE CASCADE,
     CONSTRAINT fk_message_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS users_chats (
    user_id UUID NOT NULL,
    chat_id UUID NOT NULL,
    PRIMARY KEY (user_id, chat_id),
    CONSTRAINT fk_users_chats_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_users_chats_chat FOREIGN KEY (chat_id) REFERENCES chat(id)  ON DELETE CASCADE
);
