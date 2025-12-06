--liquibase formatted sql
--changeset Andr33w:TaskManager-001
--logicalFilePath:2.0/users-insert.sql

INSERT INTO users (
    name,
    last_name,
    email,
    user_name,
    password,
    phone
)
VALUES
    ('Andrew', 'McFly', '1235@example.ru', '1ndr33w', 'qwerty123', '+91234567890'),
    ('Andrew', 'McFly', '2235@example.ru', '2ndr33w', 'qwerty123', '+21234567890'),
    ('Andrew', 'McFly', '3235@example.ru', '3ndr33w', 'qwerty123', '+31234567890'),
    ('Andrew', 'McFly', '4235@example.ru', '5ndr33w', 'qwerty123', '+41234567890'),
    ('Andrew', 'McFly', '6235@example.ru', '6ndr33w', 'qwerty123', '+61234567890'),
    ('Andrew', 'McFly', '3135@example.ru', '31dr33w', 'qwerty123', '+31134567890'),
    ('Andrew', 'McFly', '1335@example.ru', '13dr33w', 'qwerty123', '+93234567890'),
    ('Andrew', 'McFly', '2435@example.ru', '2n4r33w', 'qwerty123', '+41234527890'),
    ('Andrew', 'McFly', '3535@example.ru', '5n5r33w', 'qwerty123', '+51234567890'),
    ('Andrew', 'McFly', '4435@example.ru', '5nd433w', 'qwerty123', '+44234567890'),
    ('Andrew', 'McFly', '7235@example.ru', '67dr33w', 'qwerty123', '+61784567890'),
    ('Andrew', 'McFly', '3185@example.ru', '38dr33w', 'qwerty123', '+31184567890');
