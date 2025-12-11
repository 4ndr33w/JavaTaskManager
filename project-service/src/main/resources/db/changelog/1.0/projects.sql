--liquibase formatted sql
--changeset Andr33w:TaskManager-002
--logicalFilePath:1.0/projects.sql

CREATE TABLE IF NOT EXISTS projects (
    id              UUID            PRIMARY KEY DEFAULT uuidv7(),
    created         TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated         TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    project_name    VARCHAR(255)    NOT NULL,
    image           BYTEA,
    description     TEXT,
    admin_id        UUID            NOT NULL,
    project_status  VARCHAR(50)     NOT NULL DEFAULT 'ACTIVE'
);

CREATE INDEX IF NOT EXISTS idx_projects_admin_id ON projects(admin_id);

--rollback DROP TABLE projects;