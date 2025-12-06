--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:1.0/users-insert.sql

INSERT INTO users (name, last_name, email, user_name, password, phone)
    VALUES
('Andrew', 'McFly', '123@example.ru', 'andr33w', 'qwerty123', '+71234567890'),
('Вася', 'Пупкин', 'pup0k@example.ru', 'pup0ck', 'qwerty123', '+81234567890'),
('Andr0n', '123', 'dr0n@example.ru', '4ndr33w', 'qwerty123', '+81234567111');