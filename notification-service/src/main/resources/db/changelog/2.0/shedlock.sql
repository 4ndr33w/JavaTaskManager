--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:2.0/shedlock.sql

CREATE TABLE IF NOT EXISTS shedlock (
    name        TEXT        NOT NULL,
    lock_until  TIMESTAMP   NULL,
    locked_at   TIMESTAMP   NULL,
    locked_by   TEXT        NULL,
    CONSTRAINT pk_shedlock PRIMARY KEY (name)
);

--rollback DROP TABLE shedlock;