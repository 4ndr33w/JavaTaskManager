--liquibase formatted sql
--changeset Andr33w:TaskManager-002
--logicalFilePath:1.0/tasks.sql

CREATE TABLE IF NOT EXISTS tasks (
    id              UUID            PRIMARY KEY DEFAULT uuidv7(),
    created         TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    updated         TIMESTAMPTZ     NOT NULL DEFAULT NOW(),
    task_name       VARCHAR(255)    NOT NULL,
    image           BYTEA,
    start_time      TIMESTAMPTZ,
    end_time        TIMESTAMPTZ,
    file            BYTEA,
    file_name       VARCHAR(255),
    column_name     VARCHAR(100)    NOT NULL, -- Название колонки
    desk_id         UUID            NOT NULL REFERENCES desks(id) ON DELETE CASCADE,
    creator_id      UUID            NOT NULL,
    executor_id     UUID,
    color           VARCHAR(50)
);

CREATE INDEX IF NOT EXISTS idx_tasks_desk_id ON tasks(desk_id);
CREATE INDEX IF NOT EXISTS idx_tasks_creator_id ON tasks(creator_id);
CREATE INDEX IF NOT EXISTS idx_tasks_executor_id ON tasks(executor_id);
CREATE INDEX IF NOT EXISTS idx_tasks_column ON tasks(column_name);

--rollback DROP TABLE tasks;