CREATE TABLE IF NOT EXISTS messages (
    "id"        SERIAL PRIMARY KEY,   -- уникальный ID записи
    "player_uuid"      UUID NOT NULL, -- уникальный ID игрока, отправившего сообщение
    "text"      VARCHAR(256) NOT NULL -- содержимое Protobuf сообщения
);

CREATE INDEX IF NOT EXISTS messages_player_uuid_idx ON messages(player_uuid);