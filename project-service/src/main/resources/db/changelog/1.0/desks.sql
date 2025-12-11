--liquibase formatted sql
--changeset Andr33w:TaskManager-002
--logicalFilePath:1.0/desks.sql

CREATE TABLE IF NOT EXISTS desks (
    id          UUID            PRIMARY KEY DEFAULT uuidv7(),
    created     TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated     TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    desk_name   VARCHAR(255)    NOT NULL,
    description TEXT,
    privacy     BOOLEAN         NOT NULL DEFAULT FALSE,
    columns     TEXT[], -- JSON массив колонок
    admin_id    UUID            NOT NULL,
    project_id  UUID            NOT NULL REFERENCES projects(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_desks_project_id ON desks(project_id);
CREATE INDEX IF NOT EXISTS idx_desks_admin_id ON desks(admin_id);

--rollback DROP TABLE desks;