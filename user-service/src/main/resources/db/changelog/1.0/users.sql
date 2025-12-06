--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:1.0/users.sql

CREATE TABLE IF NOT EXISTS users (
    id          UUID            PRIMARY KEY DEFAULT uuidv7(),
    name        TEXT            NOT NULL,
    last_name   TEXT            NOT NULL,
    email       TEXT            NOT NULL UNIQUE,
    user_name   TEXT            NOT NULL UNIQUE,
    password    TEXT            NOT NULL,
    birth_date  TIMESTAMP       DEFAULT CURRENT_DATE,
    phone       TEXT            NOT NULL UNIQUE,
    created     TIMESTAMPTZ     DEFAULT now(),
    updated     TIMESTAMPTZ     DEFAULT now()
);

--rollback drop table users;