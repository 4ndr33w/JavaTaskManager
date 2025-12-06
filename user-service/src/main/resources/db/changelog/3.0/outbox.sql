--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:3.0/outbox.sql

CREATE TABLE IF NOT EXISTS outbox (
    id              UUID                        PRIMARY KEY DEFAULT uuidv7(),
    aggregate_id    UUID                        NOT NULL,
    event_type      TEXT                        NOT NULL,
    status          TEXT                        NOT NULL,
    payload         JSONB,
    created_at      TIMESTAMP WITH TIME ZONE    DEFAULT CURRENT_TIMESTAMP,
    sent_at         TIMESTAMP WITH TIME ZONE,
    retry_count     INTEGER                     DEFAULT 0
);

--rollback DROP TABLE outbox;