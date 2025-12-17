--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:1.0/inbox.sql

CREATE TABLE IF NOT EXISTS inbox (
    id              UUID                        PRIMARY KEY DEFAULT uuidv7(),
    event_id        UUID                        NOT NULL,
    status          TEXT                        NOT NULL,
    payload         JSONB,
    received_at     TIMESTAMP WITH TIME ZONE    DEFAULT CURRENT_TIMESTAMP,
    processed_at    TIMESTAMP WITH TIME ZONE
);

--rollback DROP TABLE outbox;