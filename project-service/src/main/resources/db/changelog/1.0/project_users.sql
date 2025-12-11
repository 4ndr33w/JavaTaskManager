--liquibase formatted sql
--changeset Andr33w:TaskManager-002
--logicalFilePath:1.0/project_users.sql

CREATE TABLE IF NOT EXISTS project_users (
    project_id  UUID    NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    user_id     UUID    NOT NULL,
    PRIMARY KEY (project_id, user_id)
);

--rollback DROP TABLE project_users;