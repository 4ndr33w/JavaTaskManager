--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:2.0/alter-users.sql

ALTER TABLE users
ADD COLUMN has_image    BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_blocked   BOOLEAN NOT NULL DEFAULT FALSE,
ADD COLUMN is_active    BOOLEAN NOT NULL DEFAULT TRUE;

--rollback ALTER TABLE users DROP COLUMN has_image;
--rollback ALTER TABLE users DROP COLUMN is_blocked;
--rollback ALTER TABLE users DROP COLUMN is_active;