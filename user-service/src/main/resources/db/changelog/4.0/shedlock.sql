--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:4.0/shedlock.sql

CREATE TABLE IF NOT EXISTS shedlock (
    name        TEXT        NOT NULL,
    lock_until  TIMESTAMP,
    locked_at   TIMESTAMP,
    locked_by   TEXT,
    CONSTRAINT pk_shedlock PRIMARY KEY (name)
);

--rollback DROP TABLE shedlock;