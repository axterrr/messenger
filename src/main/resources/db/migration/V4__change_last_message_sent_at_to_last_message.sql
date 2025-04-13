ALTER TABLE chat
DROP COLUMN last_message_sent_at,
ADD COLUMN last_message_id VARCHAR(36) NULL,
ADD CONSTRAINT fk_chat_last_message FOREIGN KEY (last_message_id) REFERENCES message(id) ON DELETE SET NULL;
