--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:1.0/user-roles.sql

CREATE TABLE IF NOT EXISTS user_roles (
    user_id     UUID        NOT NULL,
    role        TEXT        NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--rollback drop table user_roles;